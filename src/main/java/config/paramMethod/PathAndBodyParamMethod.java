package config.paramMethod;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

public class PathAndBodyParamMethod extends PathIParamMethod {
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        RequestSpecification requestSpecification = super.paramMethodBuild(specification, requestData);
        return requestSpecification.body(JSONObject.parseObject(requestData.getParam()));
    }
}