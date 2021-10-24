package com.example.service;

import com.example.publisher.Dto.Model;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaStreamConsumer {

    @Bean
    public Consumer<KStream<String, Model>> modelService() {
        return kstream-> kstream.foreach((key, value) ->
        {
            System.out.println("Consumed ::"+value.getDomain());
        });
    }
}
