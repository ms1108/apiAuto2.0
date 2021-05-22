package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;

public class EqualAssert extends AssertMethod {
    private String path;
    private Object expect;

    public EqualAssert(String path, Object expect) {
        this.path = path;
        this.expect = expect;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        response.then().body(path, equalTo(expect));
        backCallAssert(requestData, response);
        return this;
    }
}
