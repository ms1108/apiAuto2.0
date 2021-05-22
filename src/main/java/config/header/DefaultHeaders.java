package config.header;

import api.RequestData;
import base.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultHeaders implements IHeaders {
    @Override
    public Map<String, Object> getHeaders(RequestData requestData) {
        Map<String, Object> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("referer", requestData.getHost());
        map.put("nonce", UUID.randomUUID().toString());
        map.put("timestamp", System.currentTimeMillis());
        map.put("cookie", "TOKEN="+ DataStore.token);
        return map;
    }
}
