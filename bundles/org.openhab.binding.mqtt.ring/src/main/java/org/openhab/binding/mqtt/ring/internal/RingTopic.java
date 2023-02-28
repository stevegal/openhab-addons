package org.openhab.binding.mqtt.ring.internal;

public class RingTopic {

    private String baseTopic;
    private RingProduct ringProduct;
    private String ringId;
    private String function;
    private String functionState;

    private RingTopic() {
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public RingProduct getRingProduct() {
        return ringProduct;
    }

    public String getRingId() {
        return ringId;
    }

    public String getFunction() {
        return function;
    }

    public String getFunctionState() {
        return functionState;
    }

    public String asTopicString() {
        return baseTopic + "/" + ringProduct.getProductName() + "/" + ringId + "/" + function + "/" + functionState;
    }

    public static RingTopicBuilder newBuilder() {
        return new RingTopicBuilder();
    }

    public static class RingTopicBuilder {

        private String baseTopic;
        private RingProduct ringProduct;
        private String ringId;
        private String function;
        private String functionState;

        public RingTopicBuilder baseTopic(String baseTopic) {
            this.baseTopic = baseTopic;
            return this;
        }

        public RingTopicBuilder ringProduct(RingProduct product) {
            this.ringProduct = product;
            return this;
        }

        public RingTopicBuilder ringId(String ringId) {
            this.ringId = ringId;
            return this;
        }

        public RingTopicBuilder function(String function) {
            this.function = function;
            return this;
        }

        public RingTopicBuilder functionState(String functionState) {
            this.functionState = functionState;
            return this;
        }

        public RingTopic build() {
            RingTopic topic = new RingTopic();
            topic.baseTopic = this.baseTopic;
            topic.ringProduct = this.ringProduct;
            topic.ringId = this.ringId;
            topic.function = this.function;
            topic.functionState = this.functionState;
            return topic;
        }
    }
}
