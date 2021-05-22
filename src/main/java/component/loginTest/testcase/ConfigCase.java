package component.loginTest.testcase;

import base.BaseCase;
import component.loginTest.service_constant.LoginService;

public class ConfigCase extends BaseCase {
    public String depend;

    public ConfigCase(){
        serverMap = LoginService.Config;
    }
    public ConfigCase dependCase(){
        depend="123";
        return this;
    }
}
