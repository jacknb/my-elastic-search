package com.adelmo.esearch.template.dao;

import com.adelmo.esearch.template.common.RestConfig;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 使用RestTemplate操作ElasticSearch
 *
 * @author znb
 * @date 17-11-18.
 */
@Component
public class ElasticSearchDAO {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDAO.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

    /**
     * 查询Document
     *
     * @param index
     * @param type
     * @param requestJson
     * @return
     */
    public String queryDocument(String index, String type, String requestJson) {

        logger.info("index:{},type:{},requestJson:{}", index, type, requestJson);

        String url = "http://" + restConfig.getEsCluster() + "/" + index + "/" + type + "/_search";
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        String result;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
        } catch (RestClientException e) {
            logger.error("RestClientException", e);
            return null;
        }
        return result;
    }

    /**
     * 插入数据
     *
     * @param index
     * @param type
     * @param data
     * @return
     */
    public boolean insertDocument(String index, String type, Map<String, Object> data) {

        String result;
        String url = "http://" + restConfig.getEsCluster() + "/" + index + "/" + type + "/?pretty";
        MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<String>(data.toString(), headers);
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
            JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
            boolean resultBoolean = jsonObject.getBoolean("created");
            if (!resultBoolean) {
                return false;
            }
        } catch (RestClientException e) {
            logger.error("inertDocument error.", e);
            return false;
        }
        return true;
    }
}