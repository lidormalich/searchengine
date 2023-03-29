package com.handson.searchengine.StrategyPattern;

import com.handson.searchengine.model.CrawlerRequest;

import java.io.*;

/**
 * interface for the strategy pattern of the crawler
 */
public interface ICrawl {
    void crawl(String id, CrawlerRequest request) throws IOException, InterruptedException;

}

