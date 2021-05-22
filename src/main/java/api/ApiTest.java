package api;

import base.BaseCase;
import base.DataStore;
import component.loginTest.service_constant.LoginService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.RandomUtil;
import utils.ReportUtil;
import utils.StringUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static base.DataStore.*;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static utils.FileUtil.writeFile;

public class ApiTest {
    //该类中的属性一定要写私有的如下,因为在实体类转json串时公有属性也会被写入json中
    //private Integer test = 1;

    public Response apiTest(BaseCase baseCase) {
        return apiTest(new RequestData(baseCase));
    }

    public Response apiTest(RequestData requestData) {
        ReportUtil.printLog();
        //因为一个注解可能会发送多个接口所以预置日志打印一遍之后就清除
        ReportUtil.clearLogs();
        if (StringUtil.isEmpty(requestData.getStepDes())) {
            ReportUtil.log("Des               : " + requestData.getDes());
        } else {
            ReportUtil.log("StepDes           : " + requestData.getStepDes());
        }
        ReportUtil.log("Host              : " + requestData.getHost());
        ReportUtil.log("Uri               : " + requestData.getUri());
        ReportUtil.log("Method            : " + requestData.getMethodAndRequestType().getApiMethod());
        ReportUtil.log("ParamMethod       : " + requestData.getMethodAndRequestType().getParamMethod().getClass().getSimpleName());

        RestAssured.baseURI = requestData.getHost();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeaders().getHeaders(requestData);
        specification.headers(headers);
        ReportUtil.log("Header            : " + headers);

        specification = requestData.getMethodAndRequestType().getParamMethod().paramMethodBuild(specification, requestData);

        ReportUtil.log("Param             : " + requestData.getParam());

        if (requestData.getSleep() != null && requestData.getSleep() != 0) {
            try {
                ReportUtil.log("Sleep             : " + requestData.getSleep());
                TimeUnit.SECONDS.sleep(requestData.getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //发送请求
        Response response = requestData.getIRequestMethod().requestMethod(specification, requestData);
        //存储请求,因为取出值时是通过severMap中的Uri所以这样存的时候也是用这个存。在pathParam的情况下requestData中的Uri是会被修改的
        DataStore.req.put(requestData.getServerMap().getUri(), from(requestData.getParamData()));
        //存储响应
        DataStore.res.put(requestData.getServerMap().getUri(), response);

        //下载文件
        String ContentTypeHeader = response.getHeader("Content-Type");
        String res = response.getBody().asString();
        if (ContentTypeHeader != null && (ContentTypeHeader.contains("download")
                || ContentTypeHeader.contains("octet-stream"))) {
            String fileType = "";
            String headerDisposition = response.getHeader("Content-disposition");
            if (headerDisposition != null) {
                fileType = headerDisposition.substring(headerDisposition.lastIndexOf("."), headerDisposition.length() - 1);
            }
            String contentPath = DownloadDir + RandomUtil.getString() + fileType;
            Assert.assertTrue(writeFile(response.getBody().asInputStream(), contentPath), "下载文件失败");
            res = "{\"filePath\":\"" + contentPath + "\"}";
        }
        ReportUtil.log("res               : " + res);
        //换行
        ReportUtil.log("");

        //断言
        if (requestData.isOpenAssert() && requestData.getAssertMethod() != null) {
            requestData.getAssertMethod().assets(requestData, response);
        }

        //保存token
        if (requestData.getUri().equals(LoginService.Login.getUri())
                && response.statusCode() == 200
                && response.path(defaultAssertPath) == defaultAssertValue) {
            //BaseData.token = response.path("data.token");
        }
        return response;
    }
}
