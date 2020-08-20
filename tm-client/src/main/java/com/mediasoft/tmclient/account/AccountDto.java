package com.mediasoft.tmclient.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class AccountDto {

    private final Long id;

    private final String nick;

    private final String email;

    private final String password;
}
