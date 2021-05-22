package config.requestMethod;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DefaultRequestMethod implements IRequestMethod {
    @Override
    public Response requestMethod(RequestSpecification specification, RequestData requestData) {
        return specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUri());
    }
}
