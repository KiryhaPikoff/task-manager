package com.mediasoft.tm.account.producer;

import com.mediasoft.tm.account.producer.dto.RegistrationDataDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AccountRegistrationKafkaProducer implements AccountRegistrationProducer {

    private final KafkaTemplate<String, RegistrationDataDto> kafkaTemplate;

    @Override
    public void sendNewAccountInfo(RegistrationDataDto registrationDataDto) {
        kafkaTemplate.send("registered_accounts", registrationDataDto);
    }
}
