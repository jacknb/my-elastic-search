package com.adelmo.esearch.template.controller;

import com.adelmo.esearch.template.beans.ResponseInfo;
import com.adelmo.esearch.template.common.ServiceConsts;
import com.adelmo.esearch.template.dao.ElasticSearchDAO;
import com.adelmo.esearch.template.entity.ESearchEnum;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author znb
 * @date 17-11-18.
 */
@RestController
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private ElasticSearchDAO elasticSearchDAO;

    @RequestMapping(value = "/serviceinfo", method = RequestMethod.POST)
    public ResponseInfo insertServiceInfo(@RequestParam("serviceInfo") String serviceInfo) {

        logger.info("预插入的数据为:{}", serviceInfo);
        ResponseInfo responseInfo = new ResponseInfo();
        if (StringUtils.isEmpty(serviceInfo)) {
            responseInfo.setRetCode(ESearchEnum.PARAM_NULL.getRetCode());
            responseInfo.setRetMsg(ESearchEnum.PARAM_NULL.getRetMsg());
            responseInfo.setData(new HashMap<String, Object>(1));
            return responseInfo;
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(serviceInfo);
        Map<String, Object> dataMap = new HashMap<String, Object>(8);
        dataMap.put("service_id", jsonObject.getString("serviceId"));
        dataMap.put("service_name", jsonObject.getString("serviceName"));
        dataMap.put("system_id", jsonObject.getString("systemId"));
        dataMap.put("system_name", jsonObject.getString("systemName"));
        dataMap.put("project_id", jsonObject.getString("projectId"));
        dataMap.put("project_name", jsonObject.getString("projectName"));
        boolean result = false;
        Map<String, Object> resultMap = new HashMap<String, Object>(1);
        result = elasticSearchDAO.insertDocument(ServiceConsts.SERVICE_INDET, ServiceConsts.SERVICE_TYPE, dataMap);
        if (result) {
            responseInfo.setRetMsg(ESearchEnum.SUCCESS.getRetMsg());
            responseInfo.setRetCode(ESearchEnum.SUCCESS.getRetCode());
        } else {
            responseInfo.setRetCode(ESearchEnum.PARAM_NULL.getRetCode());
            responseInfo.setRetMsg(ESearchEnum.PARAM_NULL.getRetMsg());
        }
        resultMap.put("data", result);
        responseInfo.setData(resultMap);
        return responseInfo;
    }

}
