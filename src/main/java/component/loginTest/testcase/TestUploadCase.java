package component.loginTest.testcase;

import base.UploadCase;
import component.loginTest.service_constant.LoginService;

public class TestUploadCase extends UploadCase {
    public TestUploadCase() {
        serverMap = LoginService.Upload;
    }

    public TestUploadCase uploadCase() {
        filePath = "moco/test.json";
        return this;
    }
}
