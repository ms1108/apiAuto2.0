package api;

import base.BaseCase;
import base.DataStore;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.ContentType;
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
    //被BaseCase类继承了的类中的属性一定要写私有的如下,因为在实体类转json串时公有属性也会被写入json中。并且不能加上对应各get方法
    //private Integer test = 1;

    public Response apiTest(BaseCase baseCase) {
        return apiTest(new RequestData(baseCase));
    }

    public Response apiTest(RequestData requestData) {
        if (StringUtil.isEmpty(requestData.getStepDes())) {
            ReportUtil.log("Des               : " + requestData.getDes());
        } else {
            ReportUtil.log("StepDes           : " + requestData.getStepDes());
        }
        ReportUtil.log("Host              : " + requestData.getHost());
        ReportUtil.log("Uri               : " + requestData.getUrl());
        ReportUtil.log("Method            : " + requestData.getMethodAndRequestType().getApiMethod());
        ReportUtil.log("ParamMethod       : " + requestData.getMethodAndRequestType().getParamMethod().getClass().getSimpleName());

        RestAssured.baseURI = requestData.getHost();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeader();
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
        Response response = requestData.getInvokeRequest().invokeRequest(specification, requestData);
        //篡改响应，比如对响应解码等操作
        Response newResponse = new ResponseBuilder().clone(response).setContentType(ContentType.JSON).build();

        //存储请求
        DataStore.req.put(requestData.getIApi().getUUID(), from(requestData.getParamData()));
        //存储响应
        DataStore.res.put(requestData.getIApi().getUUID(), response);

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
            String contentPath = downloadDir + RandomUtil.getString() + fileType;
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

        return response;
    }
}
