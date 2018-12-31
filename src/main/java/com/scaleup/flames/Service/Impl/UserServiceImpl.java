package com.scaleup.flames.Service.Impl;

import com.scaleup.flames.Service.UserService;
import com.scaleup.flames.domain.User;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private RestTemplate rest;

    @Value("${solr.my.url}")
    private String solrUrl;


    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    static List<String> cookies = null;
    @Override
    public List<String> loginUser(String username, String password) {
        //LOG.info("Absolute URL is '{}'", conf.getIatAdminLoginResource());

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("user", username);
        parameters.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,
                headers);
        ResponseEntity<String> resEntity = rest.postForEntity("http://localhost:8080/user/login", entity, String.class);
        System.out.println(resEntity);
        if (resEntity != null && resEntity.getStatusCode() == HttpStatus.FOUND) {
            cookies = resEntity.getHeaders().get("Set-Cookie");
            LOG.info("Login Response is '{}'" + resEntity.getHeaders().get("Set-Cookie"));
        }
        return cookies;

    }

    @Override
    public User userDetails(User user, HttpServletResponse response) {
        HttpSolrClient solr = new HttpSolrClient.Builder(solrUrl).build();
        solr.setParser(new XMLResponseParser());
        SolrDocumentList docs = null;
        try {
            SolrQuery query = new SolrQuery();
            query.addFilterQuery("email:" + user.getEmail());
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
            LOG.info("Error while updating user details's", e.getMessage());
        }

        return user;

    }
    }
}
