package com.scaleup.flames.Service.Impl;

import com.scaleup.flames.Service.RegisterService;
import com.scaleup.flames.domain.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Value("${solr.my.url}")
    private String solrUrl;

    @Autowired
    private SolrClient solrClient;

    @Override
    public User userWithEmail(String email) {


        return null;
    }

    @Override
    public User userWithMobile(String mobile) {
        return null;
    }

    @Override
    public String registerAccount(User user) {

        HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        solr.setParser(new XMLResponseParser());
        //Preparing the Solr document
        SolrInputDocument document = new SolrInputDocument();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
        int randomNumber = ThreadLocalRandom.current().nextInt(10000);

        document.addField("id", String.valueOf(randomNumber));
        document.addField("firstName", user.getFirstName());
        document.addField("age", user.getAge());
        document.addField("lastName", user.getLastName());
        document.addField("mobileNumber", user.getMobileNumber());
        document.addField("active", true);
        document.addField("verified", true);
        document.addField("gender", user.getGender());
        document.addField("password", user.getPassword());
        document.addField("language", user.getLanguage());
        document.addField("city", user.getCity());
        document.addField("country", user.getCountry());

        updateRequest.add(document);
        try {
            UpdateResponse rsp = updateRequest.process(solr);
            solr.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Documents updated!!");
        return String.valueOf(randomNumber);
    }
}
