package com.kafka.producer.controller;

import com.kafka.producer.dto.Customer;
import com.kafka.producer.service.KafkaEventsPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kafka.producer.utils.KafkaUtils.TOPIC1_NAME;

@RestController
@RequestMapping("/producer/publish/customers")
@RequiredArgsConstructor
public class CustomerEventController {
    private final KafkaEventsPublisher kafkaEventsPublisher;

    @PostMapping
    public ResponseEntity<?> publishCustomer(@RequestBody Customer customer) {
        try {
            kafkaEventsPublisher.sendEventToTopic(TOPIC1_NAME, customer);
            return ResponseEntity.ok("Customer published successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
