package config.invokerequest;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//调用发起请求的方式，兼容了新增幂等请求的实现
public interface InvokeRequest {
    Response invokeRequest(RequestSpecification specification, RequestData requestData);
}
