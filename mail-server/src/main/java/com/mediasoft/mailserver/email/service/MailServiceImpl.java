package com.mediasoft.mailserver.email.service;

import com.mediasoft.mailserver.account.consumer.dto.RegistrationDataDto;
import com.mediasoft.mailserver.email.facade.EmailFacade;
import com.mediasoft.mailserver.email.model.MailMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MailServiceImpl implements MailService {

    private final EmailFacade emailFacade;

    //@Value("mail.template.greeting")
    private final String greetingTemplateName = "greeting/greeting.html";

    @Async
    @Override
    public void sendGreeting(RegistrationDataDto registrationDataDto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("email", registrationDataDto.getEmail());
        variables.put("nick", registrationDataDto.getNick());
        variables.put("ip", registrationDataDto.getIp());
        emailFacade.sendMessage(
                MailMessage.builder()
                        .to(registrationDataDto.getEmail())
                        .subject(registrationDataDto.getNick() + ", спасибо за регистрацию!")
                        .template(this.greetingTemplateName)
                        .variables(variables)
                        .build()
        );
    }
}