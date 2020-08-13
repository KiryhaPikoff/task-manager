package com.mediasoft.mailserver.account.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediasoft.mailserver.account.consumer.dto.RegistrationDataDto;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AccountRegistrationKafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(AccountRegistrationKafkaConsumer.class);

    @SneakyThrows
    @KafkaListener(topics = "${spring.kafka.consumer.topics.registered_accounts}")
    public void consume(RegistrationDataDto registrationDataDto) {
        var body = new ObjectMapper().writeValueAsString(registrationDataDto);
        logger.info("We are receive new message: " + body);
    }
}
