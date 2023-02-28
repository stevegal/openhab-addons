package org.openhab.binding.mqtt.ring.internal.handler;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.mqtt.ring.internal.RingBindingConstants;
import org.openhab.binding.mqtt.ring.internal.RingProduct;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;

@Component(service = ThingHandlerFactory.class)
public class RingThingHandlerFactory extends BaseThingHandlerFactory {

    private int SUBSCRIPTION_TIMEOUT = 5;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return RingBindingConstants.SUPPORTED_THINGS.containsValue(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        // should use factory here
        String productName = thing.getProperties().get(RingBindingConstants.PRODUCT_PROPERTY_KEY);
        RingProduct ringProduct = RingProduct.valueOf(productName);
        switch (ringProduct) {
            case CAMERA:
                return new RingCameraThingHandler(thing, SUBSCRIPTION_TIMEOUT);
            case CHIME:
                return new RingChimeThingHandler(thing, SUBSCRIPTION_TIMEOUT);
            default:
                return new RingUnknownThingHandler(thing, SUBSCRIPTION_TIMEOUT);
        }
    }
}
