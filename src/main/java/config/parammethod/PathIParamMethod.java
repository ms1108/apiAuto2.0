package config.parammethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class PathIParamMethod implements IParamMethod {

    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        String uri = requestData.getUrl();
        String pathParam = requestData.getIParamPreHandle().paramPathPreHandle(requestData.getPathParam());
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        requestData.setUrl(uri + pathParam);
        return specification;
    }
}
