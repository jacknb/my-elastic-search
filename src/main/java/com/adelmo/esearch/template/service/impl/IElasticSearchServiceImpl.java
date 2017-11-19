package com.adelmo.esearch.template.service.impl;

import com.adelmo.esearch.template.dao.ElasticSearchDAO;
import com.adelmo.esearch.template.service.IElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author znb
 * @date 17-11-19.
 */
public class IElasticSearchServiceImpl implements IElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(IElasticSearchServiceImpl.class);

    @Autowired
    private ElasticSearchDAO elasticSearchDAO;

    @Override
    public boolean saveDocument(String index, String type, Map<String, Object> data) {

        logger.info("index:{},type:{},data:{}", index, type, data);

        boolean result = elasticSearchDAO.insertDocument(index, type, data);

        return result;
    }
}
