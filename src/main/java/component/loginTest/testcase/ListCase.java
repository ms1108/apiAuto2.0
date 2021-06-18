package component.loginTest.testcase;

import annotation.annotations.DataDepend;
import annotation.annotations.Search;
import base.BaseListCase;
import component.loginTest.apienum.DemoApiEnum;

public class ListCase extends BaseListCase {
    @Search(addDataBaseCase = AddDataCase.class, searchValuePath = "loginName")
    public String search;

    public ListCase() {
        iApi = DemoApiEnum.List;
    }

    @DataDepend
    public void dataDepend() {
        apiTest(new AddDataCase().rightCase());
        apiTest(new AddDataCase().rightCase());
    }
}
