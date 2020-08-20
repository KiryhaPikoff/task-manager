package com.mediasoft.tmclient.client;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TestScheduler {

    private final TaskManagerClient taskManagerClient;

    private final Logger logger = LoggerFactory.getLogger(TestScheduler.class);

    private String token;

    @Scheduled(fixedDelay = 10000)
    private void authenticate() {
        logger.error("Authentication...");
        this.token = taskManagerClient.authenticate(
                Credentials.builder()
                .email("pikov_kirya@mail.ru")
                .password("Demo1234")
                .build()
        ).getHeaders().getFirst("Authorization");
        logger.error("We receive token: " + this.token);
    }

    @Scheduled(fixedDelay = 5000)
    public void getAccountInfo() {
        logger.error("Get account info...");
        logger.error(
                "Info: " +
                taskManagerClient.getAccountInfo(
                    48L, this.token
                )
        );
    }
}
