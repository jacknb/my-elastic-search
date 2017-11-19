package com.adelmo.esearch.template.beans;

import com.adelmo.esearch.template.entity.ESearchEnum;

import java.util.Map;

/**
 * @author znb
 * @date 17-11-18.
 */
public class ResponseInfo {

    /**
     * 返回编码
     */
    private String retCode;

    /**
     * 返回值
     */
    private String retMsg;

    /**
     * 返回数据
     */
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

    public ResponseInfo(String retCode, String retMsg, Map<String, Object> data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
