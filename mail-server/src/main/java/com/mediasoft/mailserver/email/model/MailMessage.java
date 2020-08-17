package com.mediasoft.mailserver.email.model;

import lombok.Getter;
import lombok.Builder;

import java.util.Map;

@Getter
@Builder
public class MailMessage {

    private final String to;
    private final String subject;
    private final String template;
    private final Map<String, Object> variables;
}