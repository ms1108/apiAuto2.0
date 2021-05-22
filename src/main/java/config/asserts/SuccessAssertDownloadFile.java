package config.asserts;

import api.RequestData;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.FileUtil;

import java.io.File;

public class SuccessAssertDownloadFile extends AssertMethod {
    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        String path = FileUtil.getNewestFileContentPath();
        File file = new File(path);
        if (file.length() == 0) {
            Assert.fail("下载的文件大小为0");
        }
        backCallAssert(requestData, response);
        return this;
    }
}
