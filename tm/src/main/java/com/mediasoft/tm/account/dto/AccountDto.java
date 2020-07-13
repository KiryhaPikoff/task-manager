package com.mediasoft.tm.account.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotBlank(message = "Это поле является обязательным")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-_.]{1,20}$",
            message = "Неверный формат ника")
    private final String nick;

    @NotBlank(message = "Это поле является обязательным")
    @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$",
            message = "Неверный формат почты")
    private final String email;

    @NotBlank(message = "Это поле является обязательным")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,}$",
            message = "Пароль не соответствует требованиям безопасности")
    private final String password;
}
