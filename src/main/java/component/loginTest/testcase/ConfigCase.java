package component.loginTest.testcase;

import base.BaseCase;
import component.loginTest.service_constant.LoginService;

public class ConfigCase extends BaseCase {
    public String depend;

    public ConfigCase() {
        serverMap = LoginService.Config;
    }

    public void dataDepend() {
        System.out.println("调用了默认的ConfigCase的依赖");
        apiTest(config());
    }

    public ConfigCase config() {
        depend = "123";
        return this;
    }
}
