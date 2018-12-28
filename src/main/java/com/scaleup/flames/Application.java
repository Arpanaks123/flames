package com.scaleup.flames;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SolrAutoConfiguration.class})
@ComponentScan(basePackages = "com.scaleup.flames.*")
public class Application {

    @Value("${solr.my.url}")
    private String solrUrl;

    @Value("${solr.user.collection-name}")
    private String solrCollectionname;

    @Value("${solr.timeout-ms}")
    private int solrTimeoutMs;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SolrClient solrClient() throws MalformedURLException {
        return new HttpSolrClient(solrUrl);
    }



}
