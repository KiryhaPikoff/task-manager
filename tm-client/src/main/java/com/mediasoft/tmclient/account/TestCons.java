package com.mediasoft.tmclient.account;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TestCons {

    private final AccountClient accountClient;

    private final Logger logger = LoggerFactory.getLogger(TestCons.class);

    @Scheduled(fixedDelay = 5000)
    public void get() {
        logger.error("Into scheduler...");
        logger.error(
                "We receive message: " +
                accountClient.getTestString((long) (Math.random() * 100))
        );
    }
}
