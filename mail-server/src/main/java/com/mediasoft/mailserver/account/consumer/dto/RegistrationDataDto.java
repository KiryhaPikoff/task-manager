package com.mediasoft.mailserver.account.consumer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class RegistrationDataDto {

    private final String ip;

    private final String nick;

    private final String email;

    private final String password;
}