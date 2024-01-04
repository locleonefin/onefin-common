package com.onefin.ewallet.common.base.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackupService.class);

    @Autowired
    protected RestTemplateHelper restTemplateHelper;

    @Async("asyncExecutor")
    public Map<String, Object> backup(String url, String requestId, Object data, String type) {
        //LOGGER.info("== Send backup request {} - url: {}", data, url);
        try {
            Map<String, Object> dataBackup = new HashMap();
            dataBackup.put("requestId", requestId);
            dataBackup.put("data", data);
            dataBackup.put("type", type);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HashMap<String, String> headersMap = new HashMap<String, String>();
            headers.keySet().forEach(header -> {
                headersMap.put(header, headers.getFirst(header));
            });
            HashMap<String, String> urlParameters = new HashMap<>();
            List<String> pathVariables = new ArrayList<String>();
            ResponseEntity<Map> responseEntity = restTemplateHelper.post(url, MediaType.APPLICATION_JSON_VALUE,
                    headersMap, pathVariables, urlParameters, null, dataBackup, new ParameterizedTypeReference<Map>() {
                    });
            //LOGGER.info("== Success receive response{}", responseEntity.getBody());
            return responseEntity.getBody();
        } catch (Exception e) {
            LOGGER.error("Fail to store {} {} {} in to backup database", url, type, data,e);
            return null;
        }
    }

}
