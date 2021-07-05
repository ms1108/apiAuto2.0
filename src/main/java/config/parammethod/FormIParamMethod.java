package config.parammethod;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

public class FormIParamMethod implements IParamMethod {
    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        return specification.formParams(JSONObject.parseObject(requestData.getParam()));
    }
}
