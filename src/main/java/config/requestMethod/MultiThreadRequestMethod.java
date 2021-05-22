package config.requestMethod;

import api.RequestData;
import config.asserts.SuccessAssertDefault;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import utils.ReportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static io.restassured.RestAssured.given;

public class MultiThreadRequestMethod implements Runnable, IRequestMethod {
    private RequestData requestData;
    private List<Response> responses = new ArrayList<>();
    private Lock lock = new ReentrantLock();//锁要在属性中new，不能在run方法中new，不然锁无效，因为多个线程就new出了多个锁不合理

    public MultiThreadRequestMethod() {
    }

    public MultiThreadRequestMethod(RequestData requestData) {
        this.requestData = requestData;
    }

    @SneakyThrows
    @Override
    public void run() {
        //重新创建specification的目的：子线程发送请求时都会用到given()，会出现死锁问题，所以每个线程都创建一份独立的specification
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeaders().getHeaders(requestData);
        specification.headers(headers);

        specification = requestData.getMethodAndRequestType().getParamMethod().paramMethodBuild(specification, requestData);

        //发送请求
        Response response = specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUri());

        //存储响应时加锁
        lock.lock();
        this.responses.add(response);
        lock.unlock();
    }

    @SneakyThrows
    @Override
    public Response requestMethod(RequestSpecification specification, RequestData requestData) {
        int multiThreadNum = requestData.getMultiThreadNum();
        MultiThreadRequestMethod multiThreadRequest = new MultiThreadRequestMethod(requestData);
        List<Thread> threads = new ArrayList<>();
        //通过起多线程同时快速的向后端发起多个相同参数的请求
        for (int i = 0; i < multiThreadNum; i++) {
            Thread thread = new Thread(multiThreadRequest);
            thread.start();
            threads.add(thread);
        }
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        if (requestData.isOpenAssert()){
            for (Response response : responses) {
                ReportUtil.log("res               : " + response.getBody().asString());
                requestData.getAssertMethod().assets(requestData,response);
            }
            //已经断言了外边不用再断言
            requestData.setOpenAssert(false);
        }
        return multiThreadRequest.responses.get(0);
    }
}
