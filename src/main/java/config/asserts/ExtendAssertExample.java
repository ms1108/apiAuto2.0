package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

/**
 * 断言继承，调用了父类断言,断言继承示例
 */
public class ExtendAssertExample extends SuccessAssertDownloadFile{
    public AssertMethod assets(RequestData requestData, Response response) {
        super.assets(requestData, response);

        backCallAssert(requestData, response);

        return this;
    }
}
