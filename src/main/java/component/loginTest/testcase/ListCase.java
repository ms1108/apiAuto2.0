package component.loginTest.testcase;

import annotation.annotations.DataDepend;
import annotation.annotations.Search;
import base.BaseListCase;
import component.loginTest.service_constant.LoginService;

public class ListCase extends BaseListCase {
    @Search(addDataBaseCase = LoginCase.class, searchValuePath = "loginName")
    public String search;

    public ListCase() {
        serverMap = LoginService.List;
    }

    @DataDepend
    public void beforeClass() {
        apiTest(new LoginCase().rightLoginCase());
        apiTest(new LoginCase().rightLoginCase());
    }
}
