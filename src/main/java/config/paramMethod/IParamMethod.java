package config.paramMethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public interface IParamMethod {
    RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData);
}
