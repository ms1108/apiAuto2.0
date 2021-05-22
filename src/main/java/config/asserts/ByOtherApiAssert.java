package config.asserts;

import api.RequestData;
import base.BaseCase;
import base.IServiceMap;
import io.restassured.response.Response;

/**
 * 调用其他接口进行断言
 */
public class ByOtherApiAssert extends AssertMethod {
    private BaseCase baseCase;

    public ByOtherApiAssert(BaseCase baseCase) {
        this.baseCase = baseCase;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {

        RequestData data = new RequestData(baseCase);

        //调用其他接口进行断言
        Response otherResponse = apiTest(data);

        backCallAssert(data, otherResponse);
        return this;
    }
}
