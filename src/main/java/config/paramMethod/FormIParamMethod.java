package config.paramMethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class FormIParamMethod implements IParamMethod {
    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        return specification.formParam(requestData.getParam());
    }
}
