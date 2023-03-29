package com.handson.searchengine.controller;

import com.handson.searchengine.aws.ProducerSQS;
import com.handson.searchengine.crawler.Crawler;
import com.handson.searchengine.crawler.CrawlerAsync;
import com.handson.searchengine.kafka.Producer;
import com.handson.searchengine.model.CrawlStatus;
import com.handson.searchengine.model.CrawlStatusOut;
import com.handson.searchengine.model.CrawlerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class AppController {

    private static final int ID_LENGTH = 6;
    private Random random = new Random();

    private final Logger logger = Logger.getLogger(CrawlerAsync.class.getName());

//    @Autowired
//    Crawler crawler;

//    @Autowired
//    Producer producer;

    @Autowired
    ProducerSQS producerSQS;

    @Autowired
    CrawlerAsync crawlerAsync;

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;
    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

//    @RequestMapping(value = "/sendKafka", method = RequestMethod.POST)
//    public String sendKafka(@RequestBody CrawlerRequest request) throws IOException, InterruptedException {
//        producer.send(request);
//        return "OK";
//    }

    @RequestMapping(value = "/sendSQS", method = RequestMethod.POST)
    public String sendSQS(@RequestBody CrawlerRequest request) throws IOException, InterruptedException {
        //        try{}catch ()

        producerSQS.send(request);
        return "OK";
    }

//    @RequestMapping(value = "/crawl", method = RequestMethod.POST)
//    public String crawl(@RequestBody CrawlerRequest request) throws IOException, InterruptedException {
//        String crawlId = generateCrawlId();
//        if (!request.getUrl().startsWith("http")) {
//            request.setUrl("https://" + request.getUrl());
//        }
//        new Thread(()-> {
//            try {
//                crawler.crawl(crawlId, request);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        return crawlId;
//    }

    @RequestMapping(value = "/crawlSQS", method = RequestMethod.POST)
    public String crawlSQS(@RequestBody CrawlerRequest request) throws IOException, InterruptedException {
        String crawlId = generateCrawlId();

        if (!request.getUrl().startsWith("http")) {
            request.setUrl("https://" + request.getUrl());
        }

        // start the crawl -> the thread simulates async work
        new Thread(() -> {
            try {
                crawlerAsync.crawl(crawlId, request);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return crawlId;
    }

//    @RequestMapping(value = "/crawl/{crawlId}", method = RequestMethod.GET)
//    public CrawlStatusOut getCrawl(@PathVariable String crawlId) throws IOException, InterruptedException {
//        return crawler.getCrawlInfo(crawlId);
//    }

    @RequestMapping(value = "/crawlSQS/{crawlId}", method = RequestMethod.GET)
    public CrawlStatusOut getCrawlSQS(@PathVariable String crawlId) throws IOException, InterruptedException {
        return crawlerAsync.getCrawlInfo(crawlId);
    }

    private String generateCrawlId() {
        String charPool = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            res.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return res.toString();
    }
}