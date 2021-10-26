package com.example;

import com.example.CustomClasses.CustomSerde;
import com.example.publisher.Dto.Model;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Getter
@Setter
@Component
public class StreamProcessor {

    private static String APP_NAME = "StreamProcessor";
    private static String BOOTSTRAP_SERVER = "localhost:9092";
    private static String CONSUMER_TOPIC = "WebDomain";
    private static String ACTIVE_TOPIC = "ConsumerDomain";
    private static String INACTIVE_TOPIC = "InactiveTopic";

    private static KafkaStreams streams;


    public static void startProcessor() {
        Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, APP_NAME);
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
//        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Model> kstream = builder.stream(CONSUMER_TOPIC, Consumed.with(Serdes.String(), CustomSerde.modelSerde()));
        KStream<String, Model> activeDomain = kstream.filter((key, val)->!val.getIsDead());
        KStream<String, Model> inactiveDomain = kstream.filter((key, val)->val.getDomain().indexOf("google")>0);
        activeDomain.foreach((key, val)->{
            System.out.println("Active Domain==========================");
            System.out.println("key----"+key);
            System.out.println("val----"+val);
        });
        inactiveDomain.foreach((key, val)->{
            System.out.println("Inactive Domain==========================");
            System.out.println("key----"+key);
            System.out.println("val----"+val);
        });
        activeDomain.to(ACTIVE_TOPIC, Produced.with(Serdes.String(), CustomSerde.modelSerde()));
        inactiveDomain.to(INACTIVE_TOPIC, Produced.with(Serdes.String(), CustomSerde.modelSerde()));
        Topology topology = builder.build();
        streams = new KafkaStreams(topology, prop);
        streams.start();

        /*Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Closing stream...............");
            streams.close();
        }));
*/

    }

    @PreDestroy
    public void destroy() {
        System.out.println("Closing stream...............");
        System.out.println("Closing stream...............");
        System.out.println("Closing stream...............");
        System.out.println("Closing stream...............");
        System.out.println("Closing stream...............");
        streams.close();
    }
}
