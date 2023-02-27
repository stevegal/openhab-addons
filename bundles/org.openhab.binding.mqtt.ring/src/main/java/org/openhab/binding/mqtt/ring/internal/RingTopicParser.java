package org.openhab.binding.mqtt.ring.internal;

public class RingTopicParser {

    public RingTopic parseTopic(String topic) {
        String[] rawStrings = topic.split("/");
        return RingTopic.newBuilder().baseTopic(rawStrings[0] + "/" + rawStrings[1])
                .ringProduct(RingProduct.fromName(rawStrings[2]).get()).ringId(rawStrings[3]).function(rawStrings[4])
                .functionState(rawStrings[5]).build();
    }
}
