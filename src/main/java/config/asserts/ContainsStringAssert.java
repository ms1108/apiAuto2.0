package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.containsString;

public class ContainsStringAssert extends AssertMethod{
    public String expect;

    public ContainsStringAssert(String expect) {
        this.expect = expect;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        response.then().body(containsString(expect));
        backCallAssert(requestData, response);
        return this;
    }
}
