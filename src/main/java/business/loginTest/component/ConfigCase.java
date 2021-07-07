package business.loginTest.component;

import base.BaseCase;
import business.loginTest.apienum.DemoApiEnum;
import lombok.Data;

import static base.DataStore.getResponseValue;

@Data
public class ConfigCase extends BaseCase {
    public String depend = getResponseValue(DemoApiEnum.Config, "res.depend");
    public Test test = new Test();

    public class Test {
        public String myTest = getResponseValue(DemoApiEnum.Config, "res.depend");
    }

    public ConfigCase() {
        iApi = DemoApiEnum.Config;
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
