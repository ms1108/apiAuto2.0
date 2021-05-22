package runxml;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class RunXml {
    public static void main(String[] args) {
        RunXml runXml = new RunXml();
        runXml.test();
    }
    public void test(){
        System.setProperty("env","test");
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("src/main/resources/testngxml/testng-login.xml");
        testNG.setTestSuites(suites);
        testNG.run();
    }
}
