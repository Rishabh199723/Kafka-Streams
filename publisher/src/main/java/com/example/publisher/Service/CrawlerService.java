package com.example.publisher.Service;

import com.example.publisher.Dto.DomainList;
import com.example.publisher.Dto.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CrawlerService {

    @Autowired
    KafkaTemplate<String, Model> kafkaTemplate;

    @Value("${kafka.topic.name}")
    public String TOPIC_NAME;

    public void crawl(String domainName) {
        System.out.println("TOPIC---"+TOPIC_NAME);
        String uri = "https://api.domainsdb.info/v1/domains/search?domain="+domainName;
        Mono<DomainList> domainListMono = WebClient.create().get().uri(uri)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(DomainList.class);
        domainListMono.subscribe(domainList -> {
            domainList.getDomains().forEach(model -> {
                ListenableFuture<SendResult<String, Model>> future =kafkaTemplate.send(TOPIC_NAME, model);
                future.addCallback(new ListenableFutureCallback<SendResult<String, Model>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println("Error occurred");
                        ex.printStackTrace();
                    }

                    @Override
                    public void onSuccess(SendResult<String, Model> result) {
                        System.out.println("Successfully published model :"+result.toString());
                    }
                });
                System.out.println("Published "+model.getDomain());
            });
        });
    }
}
