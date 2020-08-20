package com.mediasoft.tmclient.client;

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
