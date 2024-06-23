package com.kafka.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    /**
     * The NewTopic bean causes the topic to be created on the broker; it is not needed if the topic already exists.
     * @return NewTopic
     */
    @Bean
    public NewTopic topic1() {
        return new NewTopic("topic1", 3, (short) 1);
    }

    /**
     * Next three methods are equivalent to application.yml
     * spring:
     *   kafka:
     *     producer:
     *       bootstrap-servers: localhost:9092
     *       key-serializer: org.apache.kafka.common.serialization.StringSerializer
     *       value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
     *
     * @param producerFactory ProducerFactory<String, Object>
     * @return KafkaTemplate<String, Object>
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(sendProps());
    }

    private Map<String, Object> sendProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}
