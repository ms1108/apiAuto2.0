package component.loginTest.testcase;

import base.UploadCase;
import component.loginTest.apienum.DemoApiEnum;

public class TestUploadCase extends UploadCase {
    public TestUploadCase() {
        iApi = DemoApiEnum.Upload;
    }

    public TestUploadCase uploadCase() {
        filePath = "moco/test.json";
        return this;
    }

}
