package com.adelmo.esearch.template.dao;

import com.adelmo.esearch.template.common.RestConfig;
import com.alibaba.fastjson.JSON;
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
        JSONObject dataObject = JSONObject.parseObject(JSON.toJSONString(data));

        HttpEntity<String> entity = new HttpEntity<String>(dataObject.toString(), headers);
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

    /**
     * 根据_id删除数据
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    public boolean deleteById(String index, String type, String id) {
        if (isEmpty(index, type, id)) {
            return false;
        }
        boolean found;
        try {
            String url = "http://" + restConfig.getEsCluster() + "/" + index + "/" + type + "/" + id;
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(mediaType);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            String result = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class).getBody();
            JSONObject resultObject = (JSONObject) JSONObject.parse(result);
            found = resultObject.getBoolean("found");
        } catch (RestClientException e) {
            logger.error("deleteById RestClientException.", e);
            return false;
        }
        return found;
    }

    /**
     * 根据id修改数据
     *
     * @param index
     * @param type
     * @param id
     * @param params
     * @return
     */
    public boolean updateById(String index, String type, String id, Map<String, Object> params) {
        if (isEmpty(index, type, id)) {
            return false;
        }
        String dataJson = this.updateJSon(params);
        try {
            String url = "http://" + restConfig.getEsCluster() + "/" + index + "/" + type + "/" + id + "/_update?pretty";
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(mediaType);
            HttpEntity<String> entity = new HttpEntity<String>(dataJson.toString(), headers);
            String resultStr = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
            JSONObject resultObject = (JSONObject) JSONObject.parse(resultStr);
            int failed = resultObject.getJSONObject("_shards").getInteger("failed");
            if (failed > 0) {
                return false;
            }
        } catch (RestClientException e) {
            logger.error("updateById exception.", e);
            return false;
        }
        return true;
    }

    /**
     * 生成修改文档的json
     *
     * @param params
     * @return
     */
    private String updateJSon(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            jsonObject.put(param.getKey(), param.getValue());
        }
        String result = "{\"doc\": {\n" + jsonObject.toString() + "},\"doc_as_upsert\": true}";
        return result;
    }

    /**
     * 判断参数是否为空
     *
     * @param index
     * @param type
     * @param id
     * @return
     */
    private boolean isEmpty(String index, String type, String id) {
        if (isIndexAndTypeNull(index, type)) {
            return true;
        }
        if (id == null || "".equals(id)) {
            return true;
        }
        return false;
    }

    /**
     * 判断index和type是否为空
     *
     * @param index
     * @param type
     * @return
     */
    private boolean isIndexAndTypeNull(String index, String type) {
        if (index == null || "".equals(index)) {
            return true;
        }
        if (type == null || "".equals(type)) {
            return true;
        }
        return false;
    }
}
