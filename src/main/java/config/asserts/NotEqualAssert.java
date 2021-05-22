package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class NotEqualAssert extends AssertMethod {
    private String path;
    private Object expect;

    public NotEqualAssert(String path, Object expect) {
        this.path = path;
        this.expect = expect;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        response.then().body(path, not(equalTo(expect)));
        backCallAssert(requestData, response);
        return this;
    }
}
