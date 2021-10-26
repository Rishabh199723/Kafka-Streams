package com.example.CustomClasses;

import com.example.publisher.Dto.Model;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomSerde {

    public static class ModelSerde extends Serdes.WrapperSerde<Model> {
        public ModelSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>(Model.class));
        }
    }

    public static Serde<Model> modelSerde(){
        return new CustomSerde.ModelSerde();    }
}
