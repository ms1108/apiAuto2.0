package annotation.impl;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.MultiRequest;
import api.RequestData;
import base.BaseCase;
import config.asserts.AssertMethod;
import lombok.SneakyThrows;
import utils.StringUtil;

public class MultiRequestDefault extends IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        MultiRequest annotation = (MultiRequest) annotationTestEntity.annotation;
        BaseCase baseCaseMethod = (BaseCase) annotationTestEntity.method.invoke(annotationTestEntity.baseCaseData);
        String des = "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                ",执行@" + MultiRequest.class.getSimpleName() + ",方法名:" + annotationTestEntity.method.getName() + "，" + annotation.des();
        RequestData requestData = new RequestData(baseCaseMethod)
                .setMultiThreadNum(annotation.multiThreadNum())
                .setInvokeRequest(annotation.iRequest().newInstance())
                .setStepDes(des)
                .setOpenAssert(annotation.isOpenAssert())
                .setSleep(annotation.sleep());
        String resetAssert = annotation.resetAssert();
        if (StringUtil.isNotEmpty(resetAssert)) {
            AssertMethod retAssertMethod = (AssertMethod) baseCaseMethod.getClass().getMethod(resetAssert).invoke(baseCaseMethod);
            requestData.setAssertMethod(retAssertMethod);
        }
        apiTest(requestData);
    }
}
