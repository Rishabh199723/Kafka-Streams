package com.example.publisher.Controller;

import com.example.publisher.Service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domain")
public class DomainCrawlerController {

    @Autowired
    CrawlerService crawlerService;

    @GetMapping("/crawl/{name}")
    public String crawlDomain(@PathVariable("name") String domain) {
        crawlerService.crawl(domain);
        return "Data crawled from "+domain;
    }
}
