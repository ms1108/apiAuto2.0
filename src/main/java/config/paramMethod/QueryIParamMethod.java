package config.paramMethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class QueryIParamMethod implements IParamMethod {
    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        return specification.queryParam(requestData.getParam());
    }
}
