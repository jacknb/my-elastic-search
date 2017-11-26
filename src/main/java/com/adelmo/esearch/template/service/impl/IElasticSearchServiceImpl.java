package com.adelmo.esearch.template.service.impl;

import com.adelmo.esearch.template.dao.ElasticSearchDAO;
import com.adelmo.esearch.template.service.IElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author znb
 * @date 17-11-19.
 */
@Service
public class IElasticSearchServiceImpl implements IElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(IElasticSearchServiceImpl.class);

    @Autowired
    private ElasticSearchDAO elasticSearchDAO;

    @Override
    public String insertDocumentById(String index, String type, String queryJson) {
        logger.info("index:{},type:{},queryJson:{}", index, type, queryJson);
        String result = elasticSearchDAO.queryDocument(index, type, queryJson);
        return result;
    }

    @Override
    public boolean saveDocument(String index, String type, Map<String, Object> data) {

        logger.info("index:{},type:{},data:{}", index, type, data);

        boolean result = elasticSearchDAO.insertDocument(index, type, data);

        return result;
    }
}
