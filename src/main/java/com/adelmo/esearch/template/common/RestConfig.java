package com.adelmo.esearch.template.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author znb
 * @date 17-11-18.
 */
@Configuration
public class RestConfig {

    @Value("${es.cluster}")
    public String esCluster;

    public String getEsCluster() {
        return esCluster;
    }
}
