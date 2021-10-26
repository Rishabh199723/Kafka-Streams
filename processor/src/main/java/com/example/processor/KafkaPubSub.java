package com.example.processor;

import com.example.publisher.Dto.Model;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KafkaPubSub {

    /*@Bean("modelProcessor")
    public Function<KStream<String, Model>,KStream<String, Model>> modelProcessor() {
            return kstream->kstream.filter((key, value)->{
                if(value.getIsDead()){
                    System.out.println("Dead domain :"+value.getDomain());
                } else {
                    System.out.println("Active domain :"+value.getDomain());
                }
                return !value.getIsDead();
            });
    }*/
}
