package com.adelmo.esearch.template.beans;

import com.adelmo.esearch.template.entity.ESearchEnum;

import java.util.Map;

/**
 * @author znb
 * @date 17-11-18.
 */
public class ResponseInfo {

    private String retCode;

    private String retMsg;

    private Map<String, Object> data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ResponseInfo() {
        //默认成功
        this.retCode = ESearchEnum.SUCCESS.getRetCode();
        this.retMsg = ESearchEnum.SUCCESS.getRetMsg();
    }
}
