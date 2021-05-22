package base;

import annotation.AnnotationServer;
import annotation.annotations.DataDepend;
import annotation.annotations.BaseCaseData;
import lombok.SneakyThrows;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ReportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnotationTest extends AnnotationServer {
    //business.loginTest.testcase格式
    private String packagePath;

    public AnnotationTest(String packagePath) {
        this.packagePath = packagePath.trim();
    }

    @DataProvider
    public Object[][] executeAnnotationAble() {
        return getDataProvider();
    }

    @Test(dataProvider = "executeAnnotationAble")
    public void annotationTest(String executeAnnotationName, Class<? extends BaseCase> baseCase) {
        //预置日志前先清空，万一该用例空转，也就是没有发送接口，则导致下边的预置日志带到下一个用例中，所以先清空。
        ReportUtil.clearLogs();
        ReportUtil.setPreLog("PackagePath       : " + packagePath);
        ReportUtil.setPreLog("BaseCase          : " + baseCase.getSimpleName());
        ReportUtil.setPreLog("MethodOrFieldName : " + executeAnnotationName.split(",")[0]);
        ReportUtil.setPreLog("AnnotationName    : " + executeAnnotationName.split(",")[1]);
        //换行
        ReportUtil.setPreLog("");
        annotationServer(baseCase, executeAnnotationName);
    }

    //构造成这种格式:object[][] objects = {{"baseCaseData的方法名,annotation名称在该字段上的",Class<? extends BaseCase>}};
    @SneakyThrows
    private Object[][] getDataProvider() {


        //将List转成object[][],后边的括号定义内层元素
        //Object[][] array = new Object[allCase.size()][2];
        //for (int i = 0; i < allCase.size(); i++) {
        //    for (int j = 0; j < allCase.get(i).size(); j++) {
        //        if (j == 0) {
        //            array[i][j] = (String) allCase.get(i).get(j);
        //        } else {
        //            array[i][j] = (Class<? extends BaseCase>) allCase.get(i).get(j);
        //        }
        //    }
        //}
        return null;
    }
}