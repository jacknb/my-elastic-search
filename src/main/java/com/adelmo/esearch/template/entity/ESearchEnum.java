package com.adelmo.esearch.template.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author znb
 * @date 17-11-18.
 */
public enum ESearchEnum {

    SUCCESS("1000", "成功"),
    PARAM_NULL("1001", "参数空异常");
    private String retCode;
    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public static Map<String, String> elsearch = new HashMap<String, String>();

    ESearchEnum(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

}
