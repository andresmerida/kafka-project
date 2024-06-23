package com.kafka.producer.service;

import com.kafka.producer.dto.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaEventsPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEventToTopic(String topic, Customer customer) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, customer);
        future.whenComplete((result, e) -> {
            if (e != null) {
                System.out.println("Unable to send event to topic: " + topic
                        + ", error: " + e.getMessage());
            } else {
                System.out.println("Successfully sent event to topic: " + topic
                        + ", sent message:[" + customer.toString()
                        + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }
}
