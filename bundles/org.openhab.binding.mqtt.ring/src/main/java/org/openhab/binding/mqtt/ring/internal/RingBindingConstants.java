/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.mqtt.ring.internal;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link mqtt.ringBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author stevegal - Initial contribution
 */
@NonNullByDefault
public class RingBindingConstants {

    private static final String BINDING_ID = "mqtt";

    public static final String PRESENTATION_PROPERTY_KEY = "name";

    public static final String PRODUCT_PROPERTY_KEY = "ringProduct";

    // List of all Thing Type UIDs
    public static final ThingTypeUID CAMERA_THING_TYPE = new ThingTypeUID(BINDING_ID, "camera");
    public static final ThingTypeUID CHIME_THING_TYPE = new ThingTypeUID(BINDING_ID, "chime");
    public static final ThingTypeUID UNKNOWN_THING_TYPE = new ThingTypeUID(BINDING_ID, "unknown");

    // convenience set of supported devices
    public static final Map<RingProduct, ThingTypeUID> SUPPORTED_THINGS = Map.of(RingProduct.CAMERA, CAMERA_THING_TYPE,
            RingProduct.CHIME, CHIME_THING_TYPE, RingProduct.UNKNOWN, UNKNOWN_THING_TYPE);

    // List of all Channel ids
    public static final String CHANNEL_1 = "channel1";
}
