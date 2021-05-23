package annotation;

import api.ApiTest;
import api.RequestData;
import base.BaseCase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import config.asserts.AssertMethod;
import lombok.SneakyThrows;
import utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public abstract class IAnnotationTestMethod extends AnnotationServer {
    public abstract void testMethod(AnnotationTestEntity annotationTestEntity);

    public void replaceValue(RequestData requestData, String targetPath, Object value) {
        JSONObject jsonObj = JSON.parseObject(requestData.getParamData());
        JSONPath.set(jsonObj, targetPath, value);
        requestData.setParamData(JSON.toJSONString(jsonObj));
    }

    @SneakyThrows
    public void resetAssert(RequestData requestData, BaseCase baseCaseData, AssertMethod assertMethod, String resetAssert) {
        if (StringUtil.isNotEmpty(resetAssert)) {
            AssertMethod retAssertMethod = (AssertMethod) baseCaseData.getClass().getMethod(resetAssert).invoke(baseCaseData);
            requestData.setAssertMethod(retAssertMethod);
        } else {
            requestData.setAssertMethod(assertMethod);
        }
    }

    @SneakyThrows
    public void commonBuild(AnnotationTestEntity annotationTestEntity, AssertMethod assertMethod, String resetAssert){
        RequestData requestData = new RequestData(annotationTestEntity.baseCaseData);
        replaceValue(requestData, annotationTestEntity.fieldPath, annotationTestEntity.value);
        resetAssert(requestData, annotationTestEntity.baseCaseData, assertMethod, resetAssert);
        requestData.setStepDes(annotationTestEntity.des);
        apiTest(requestData);
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
