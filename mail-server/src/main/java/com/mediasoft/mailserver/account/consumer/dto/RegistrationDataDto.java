package com.mediasoft.mailserver.account.consumer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationDataDto {

    private String ip;

    private String nick;

    private String email;

    private String password;
}