package org.openhab.binding.mqtt.ring.internal;

import java.util.Arrays;
import java.util.Optional;

public enum RingProduct {
	
	CHIME("chime"), CAMERA("camera");
	
	private final String productName;
	
	private RingProduct(String productName) {
		this.productName = productName;
	}
	
	static Optional<RingProduct> fromName(String name) {
		return Arrays.asList(RingProduct.values()).stream().filter(p-> p.productName.equals(name)).findFirst();
	}
}
