package component.user;

import base.AnnotationTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserServer extends AnnotationTest {
    public UserServer(String packagePath) {
        super("business.user.userCase");
    }

    @BeforeClass
    public void before(){
        System.out.println("BeforeClass");
    }
    @Test
    public void test(){
        //更多断言方法http://testingpai.com/article/1599472747188
        //new UserCase().user().then().body("code",equalTo(0));
        System.out.println("test");
    }
}
