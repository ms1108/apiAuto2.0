package component.loginTest.testcase;

import base.UploadCase;
import component.loginTest.service_constant.DemoService;

public class TestUploadCase extends UploadCase {
    public TestUploadCase() {
        serverMap = DemoService.Upload;
    }

    public TestUploadCase uploadCase() {
        filePath = "moco/test.json";
        return this;
    }

    @Override
    public void dataDepend() {

    }
}
