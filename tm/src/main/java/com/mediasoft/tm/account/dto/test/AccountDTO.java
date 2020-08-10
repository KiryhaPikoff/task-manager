package com.mediasoft.tm.account.dto.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public enum AccountDTO {;

    private interface Id {
        @Positive
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        /**
         * Док к Id.
         */
        Long getId();
    }

    private interface Nick {
        @NotBlank(message = "Это поле является обязательным")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-_.]{1,20}$",
                message = "Неверный формат ника")
        /**
         * Док к Nick.
         */
        String getNick();
    }

    private interface Email {
        @NotBlank(message = "Это поле является обязательным")
        @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$",
                message = "Неверный формат почты")
        /**
         * Док к Email.
         */
        String getEmail();
    }

    private interface Password {
        @NotBlank(message = "Это поле является обязательным")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,}$",
                message = "Пароль не соответствует требованиям безопасности")
        String getPassword();
    }


    public enum Request{;
        @Value
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Create implements Id, Nick, Email, Password {
            Long id;
            String nick;
            String email;
            String password;
        }
    }

    public enum Response{;
        @Value
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Public implements Id, Nick, Email {
            Long id;
            String nick;
            String email;
        }

        @Value
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Private implements Id, Nick, Email, Password {
            Long id;
            String nick;
            String email;
            String password;
        }
    }
}
