package config.requestMethod;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface IRequestMethod {
    Response requestMethod(RequestSpecification specification, RequestData requestData);
}
