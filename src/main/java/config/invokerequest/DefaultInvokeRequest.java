package config.invokerequest;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DefaultInvokeRequest implements InvokeRequest {
    @Override
    public Response invokeRequest(RequestSpecification specification, RequestData requestData) {
        return specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUrl());
    }
}
