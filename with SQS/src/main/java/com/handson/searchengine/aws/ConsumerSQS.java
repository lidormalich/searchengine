package com.handson.searchengine.aws;

import com.fasterxml.jackson.databind.*;
import com.handson.searchengine.crawler.CrawlerAsync;
import com.handson.searchengine.model.CrawlerRecord;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cloud.aws.messaging.listener.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;

/**
 * ConsumerSQS is a class that implements the SQS consumer
 */
@Service
public class ConsumerSQS {
    Logger logger = LoggerFactory.getLogger(ConsumerSQS.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CrawlerAsync crawlerAsync;

    @SqsListener(value = "chana-queue")
    public void handlerInComingMessage(Object message) throws IOException, InterruptedException {
        if (message != null)
        {
            logger.info("Received <" + message + ">");
            CrawlerRecord crawlerRecord = objectMapper.readValue(message.toString(), CrawlerRecord.class);
            crawlerAsync.crawlOneRecorde(crawlerRecord);
        }


    }




}
