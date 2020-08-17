package com.mediasoft.mailserver.email.service;

import com.mediasoft.mailserver.account.consumer.dto.RegistrationDataDto;

public interface MailService {

    void sendGreeting(RegistrationDataDto registrationDataDto);
}
