package config.header;

import api.RequestData;

import java.util.Map;

public interface IHeaders {
    Map<String,Object> getHeaders(RequestData requestData);
}
