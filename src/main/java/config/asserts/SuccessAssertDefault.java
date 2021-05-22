package config.asserts;

import api.RequestData;
import base.DataStore;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utils.StringUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

public class SuccessAssertDefault extends AssertMethod {
    private String assertPath = DataStore.defaultAssertPath;
    private Object assertValue = DataStore.defaultAssertValue;

    public SuccessAssertDefault() {
    }

    public SuccessAssertDefault(String assertPath, Object assertValue) {
        this.assertPath = assertPath;
        this.assertValue = assertValue;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        ValidatableResponse validatableResponse = response.then().statusCode(200).body(assertPath, equalTo(assertValue));
        if (StringUtil.isNotEmpty(requestData.getJsonSchemaPath())){
            //jsonschema的模板生成：https://pypi.org/project/genson/
            // 使用：https://blog.csdn.net/swinfans/article/details/89231247
            validatableResponse.body(matchesJsonSchemaInClasspath(requestData.getJsonSchemaPath()));
        }

        backCallAssert(requestData, response);

        return this;
    }
}
