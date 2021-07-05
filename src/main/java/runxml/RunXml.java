package runxml;

import org.testng.TestNG;
import utils.set.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;
//整个组执行时，走这里
public class RunXml {
    public static void main(String[] args) {
        RunXml runXml = new RunXml();
        runXml.test();
    }
    public void test(){
        PropertiesUtil.set("env","test");
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("src/main/resources/testngxml/testng-login.xml");
        testNG.setTestSuites(suites);
        testNG.run();
    }
}
