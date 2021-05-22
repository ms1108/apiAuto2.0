package config.asserts;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.*;

public class ListSearchAssert extends AssertMethod {
    public String listRootPath;
    public String expectValue;
    public Integer expectListLen;

    public ListSearchAssert(String listRootPath, Integer expectListLen) {
        this.listRootPath = listRootPath;
        this.expectListLen = expectListLen;
    }

    public ListSearchAssert(String listRootPath, String expectValue, Integer expectListLen) {
        this.listRootPath = listRootPath;
        this.expectValue = expectValue;
        this.expectListLen = expectListLen;
    }

    public AssertMethod assets(RequestData requestData, Response response) {
        ValidatableResponse validatableResponse = response.then();

        if (expectValue != null) {
            validatableResponse.body(containsString(expectValue))
                    .body(listRootPath + ".size()",
                            describedAs("搜索后期望" + requestData.getUri() + "的列表长度为:" + expectListLen, equalTo(expectListLen)));
        } else {
            validatableResponse.body(listRootPath + ".size()",
                    describedAs(requestData.getUri()+",期望列表接口的长度不为:" + expectListLen, not(equalTo(expectListLen))));

        }

        backCallAssert(requestData, response);

        return this;
    }
}
