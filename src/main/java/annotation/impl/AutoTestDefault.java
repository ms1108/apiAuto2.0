package annotation.impl;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.AutoTest;
import api.RequestData;
import base.BaseCase;
import lombok.SneakyThrows;

public class AutoTestDefault extends IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        AutoTest annotation = (AutoTest) annotationTestEntity.annotation;
        BaseCase baseCaseTest = (BaseCase) annotationTestEntity.method.invoke(annotationTestEntity.baseCaseData);
        String des = "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                ",执行@" + AutoTest.class.getSimpleName() + ",方法名:" + annotationTestEntity.method.getName() + "，" + annotation.des();
        apiTest(new RequestData(baseCaseTest)
                .setStepDes(des)
                .setOpenAssert(annotation.isOpenAssert())
                .setSleep(annotation.sleep()));
    }
}
