package org.openhab.binding.mqtt.ring.internal.discovery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.mqtt.discovery.AbstractMQTTDiscovery;
import org.openhab.binding.mqtt.discovery.MQTTTopicDiscoveryService;
import org.openhab.binding.mqtt.ring.internal.RingBindingConstants;
import org.openhab.binding.mqtt.ring.internal.RingProduct;
import org.openhab.binding.mqtt.ring.internal.RingTopic;
import org.openhab.binding.mqtt.ring.internal.RingTopicParser;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.io.transport.mqtt.MqttBrokerConnection;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component(service = DiscoveryService.class, configurationPid = "discovery.mqtt")
@NonNullByDefault
public class RingMqttDiscovery extends AbstractMQTTDiscovery {

    private final Logger logger = LoggerFactory.getLogger(RingMqttDiscovery.class);

    @NonNullByDefault({})
    private MQTTTopicDiscoveryService mqttTopicDiscovery;

    private RingTopicParser ringTopicParser;

    private Map<String, DiscoveryResultBuilder> thingMap = new ConcurrentHashMap<>();

    private Gson gson;

    private @Nullable ScheduledFuture<?> future;

    @Activate
    public RingMqttDiscovery() {
        super(Set.copyOf(RingBindingConstants.SUPPORTED_THINGS.values()), 5, true, "ring/#");
        gson = new GsonBuilder().create();
        ringTopicParser = new RingTopicParser();
    }

    @Reference
    public void setMQTTTopicDiscoveryService(MQTTTopicDiscoveryService service) {
        mqttTopicDiscovery = service;
    }

    public void unsetMQTTTopicDiscoveryService(@Nullable MQTTTopicDiscoveryService service) {
        mqttTopicDiscovery.unsubscribe(this);
        this.mqttTopicDiscovery = null;
    }

    @Override
    public void receivedMessage(ThingUID bridge, MqttBrokerConnection connection, String topic, byte[] payload) {
        resetTimeout();
        RingTopic ringTopic = ringTopicParser.parseTopic(topic);
        logger.error("ring broker " + bridge.getBindingId());
        logger.error("ring broker " + bridge.getBindingId());
        String label = ringTopic.getRingProduct() + " (" + ringTopic.getRingId() + ")";
        DiscoveryResultBuilder inProgDiscoveryResultBuilder = thingMap.computeIfAbsent(ringTopic.getRingId(),
                Key -> DiscoveryResultBuilder
                        .create(new ThingUID(getThingTypeUID(ringTopic.getRingProduct()), bridge,
                                ringTopic.getRingId()))
                        .withProperty(RingBindingConstants.PRESENTATION_PROPERTY_KEY, label)
                        .withProperty(RingBindingConstants.PRODUCT_PROPERTY_KEY, ringTopic.getRingProduct().name())
                        .withRepresentationProperty("name").withBridge(bridge))
                .withLabel(label);
        inProgDiscoveryResultBuilder.withProperty(ringTopic.getFunction() + "_" + ringTopic.getFunctionState(),
                ringTopic);

        final ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(false);
        }
        this.future = scheduler.schedule(this::publishResults, 3, TimeUnit.SECONDS);
    }

    @Override
    public void topicVanished(ThingUID thingUID, MqttBrokerConnection connection, String topic) {
        logger.error("topic vanished: [" + topic + "] " + thingUID.getAsString());
    }

    @Override
    protected MQTTTopicDiscoveryService getDiscoveryService() {
        return this.mqttTopicDiscovery;
    }

    private void publishResults() {
        // copy before iterate to prevent concurrent race
        List<DiscoveryResultBuilder> builders = new ArrayList<>(thingMap.values());
        logger.error("found " + builders.size());
        builders.stream().forEach(builder -> thingDiscovered(builder.build()));
    }

    private ThingTypeUID getThingTypeUID(RingProduct product) {
        ThingTypeUID thingTypeUID = RingBindingConstants.SUPPORTED_THINGS.get(product);
        if (null == thingTypeUID) {
            thingTypeUID = RingBindingConstants.UNKNOWN_THING_TYPE;
        }
        return thingTypeUID;
    }
}
