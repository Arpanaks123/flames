package com.scaleup.flames.Service.Impl;

import com.scaleup.flames.Service.RegisterService;
import com.scaleup.flames.domain.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Value("${solr.my.url}")
    private String solrUrl;

    @Autowired
    private SolrClient solrClient;

    @Override
    public Object userWithEmail(String email)  {

        HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        solr.setParser(new XMLResponseParser());
        SolrDocumentList docs = null;
        try {
            SolrQuery query = new SolrQuery();
            query.addFilterQuery("email:" + email);
            query.setQuery("*:*");
            // Executing the query
            QueryResponse queryResponse = solr.query(query);
            // Storing the results of the query
            docs = queryResponse.getResults();
        } catch (Exception e) {
            logger.info("Error while checking user's details from Solr!!");
        }
        return docs;
    }

    @Override
    public User userWithMobile(String mobile) {
        return null;
    }

    @Override
    public void registerAccount (String email) {

        HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        solr.setParser(new XMLResponseParser());
        //Preparing the Solr document
        SolrInputDocument document = new SolrInputDocument();
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
        int randomNumber = ThreadLocalRandom.current().nextInt(10000000);
        document.addField("id", String.valueOf(randomNumber));
        document.addField("email", email);
        updateRequest.add(document);

        try {
            updateRequest.process(solr);
            solr.commit();
            logger.info("Documents updated!!");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User editProfile(String uuid, User user) {

        HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        solr.setParser(new XMLResponseParser());
        SolrDocumentList docs = null;
        try {
            SolrQuery query = new SolrQuery();
            query.addFilterQuery("id:" + uuid);
            query.setQuery("*:*");
            // Executing the query
            QueryResponse queryResponse = solr.query(query);
            // Storing the results of the query
            docs = queryResponse.getResults();
            if(docs.size() == 1){
            SolrDocument oldDoc = docs.get(0);
            SolrInputDocument newDoc = new SolrInputDocument();
            newDoc.addField("id", oldDoc.getFieldValue("id"));
            newDoc.addField("email", oldDoc.getFieldValue("email"));
            newDoc.addField("age", user.getAge());
            newDoc.addField("lastName", user.getLastName());
            newDoc.addField("firstName", user.getFirstName());
            newDoc.addField("mobileNumber", user.getMobileNumber());
            newDoc.addField("active", true);
            newDoc.addField("verified", true);
            newDoc.addField("gender", user.getGender());
            newDoc.addField("password", user.getPassword());
            newDoc.addField("language", user.getLanguage());
            newDoc.addField("city", user.getCity());
            newDoc.addField("country", user.getCountry());
            solr.add(newDoc);
            solr.commit();
            }
            else{
                throw new Exception("Multiple entry found with this id");
            }
        } catch(Exception e){
            logger.info("Error while updating user details's", e.getMessage());
        }

        return user;

    }
}
