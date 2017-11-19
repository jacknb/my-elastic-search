package com.adelmo.esearch.template.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author znb
 * @date 17-11-18.
 */
@Service
public interface IElasticSearchService {

    /**
     * 查询
     *
     * @param index
     * @param type
     * @param queryJson
     * @return
     */
    String insertDocumentById(String index, String type, String queryJson);

    /**
     * 保存数据到ES中
     *
     * @param index
     * @param type
     * @param data
     * @return
     */
    boolean saveDocument(String index, String type, Map<String, Object> data);
}
