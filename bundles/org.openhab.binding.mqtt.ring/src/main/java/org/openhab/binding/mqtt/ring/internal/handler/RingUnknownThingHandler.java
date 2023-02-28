package org.openhab.binding.mqtt.ring.internal.handler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.mqtt.generic.AbstractMQTTThingHandler;
import org.openhab.binding.mqtt.generic.ChannelState;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;

public class RingUnknownThingHandler extends AbstractMQTTThingHandler {

    public RingUnknownThingHandler(Thing thing, int subscribeTimeout) {
        super(thing, subscribeTimeout);
        // TODO Auto-generated constructor stub
    }

    @Override
    public @Nullable ChannelState getChannelState(ChannelUID channelUID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CompletableFuture<Void> unsubscribeAll() {
        // TODO Auto-generated method stub
        return CompletableFuture.completedFuture(null);
    }

    @Override
    protected void updateThingStatus(boolean messageReceived, Optional<Boolean> availabilityTopicsSeen) {
        // TODO Auto-generated method stub
    }
}
