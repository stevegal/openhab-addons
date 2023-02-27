package org.openhab.binding.mqtt.ring.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RingProductParserTest {

    @Test
    void parsesCameraFeed() {
        RingTopicParser parser = new RingTopicParser();

        RingTopic ringTopic = parser.parseTopic("ring/mqtt-id/camera/ring-id/snapshot/image");

        assertThat(ringTopic.getRingProduct()).isEqualTo(RingProduct.CAMERA);
        assertThat(ringTopic.getBaseTopic()).isEqualTo("ring/mqtt-id");
        assertThat(ringTopic.getRingId()).isEqualTo("ring-id");
        assertThat(ringTopic.getFunction()).isEqualTo("snapshot");
        assertThat(ringTopic.getFunctionState()).isEqualTo("image");
    }
}
