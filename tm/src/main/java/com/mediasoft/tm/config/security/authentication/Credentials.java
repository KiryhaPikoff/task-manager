package com.mediasoft.tm.config.security.authentication;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    private String email;

    private String password;
}
