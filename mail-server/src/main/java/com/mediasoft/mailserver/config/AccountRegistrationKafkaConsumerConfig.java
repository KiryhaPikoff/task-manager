package com.mediasoft.mailserver.config;

import com.mediasoft.mailserver.account.consumer.dto.RegistrationDataDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class AccountRegistrationKafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        return props;
    }

    @Bean
    public ConsumerFactory<String, RegistrationDataDto> consumerFactory() {
        var jsonDeserializer = new JsonDeserializer<>(RegistrationDataDto.class);
        jsonDeserializer.addTrustedPackages("com.mediasoft.tm.account.producer.dto");
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RegistrationDataDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RegistrationDataDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}