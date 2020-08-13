package com.mediasoft.tm.account.producer;

import com.mediasoft.tm.account.producer.dto.RegistrationDataDto;

public interface AccountRegistrationProducer {

    void sendNewAccountInfo(RegistrationDataDto registrationDataDto);
}
