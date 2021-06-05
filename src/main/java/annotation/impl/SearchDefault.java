package annotation.impl;

import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Search;
import config.asserts.ListSearchAssert;
import lombok.SneakyThrows;

import static base.DataStore.getRequestValue;

public class SearchDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        Search annotation = (Search) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",列表搜索测试,";
        //搜索值，需要先往列表中新增数据
        Object value = getRequestValue(annotation.addDataBaseCase().newInstance().getIApi(), annotation.searchValuePath());
        //用于模糊搜索annotation.expectListLen()
        String dimSearch = value.toString().substring(0, value.toString().length() - 1);
        //当ListSearchAssert传入期望值后，同时期望列表长度为expectListLen
        annotationTestEntity.value = value;
        annotationTestEntity.des = des + value;
        commonBuild(annotationTestEntity, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());

        annotationTestEntity.value = dimSearch;
        annotationTestEntity.des = "模糊搜索:" + dimSearch;
        commonBuild(annotationTestEntity, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());

        //当ListSearchAssert没有传入期望值时，期望列表长度不为expectListLen
        annotationTestEntity.value = "";
        annotationTestEntity.des = des + "空值搜索";
        commonBuild(annotationTestEntity, new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());

        annotationTestEntity.value = null;
        annotationTestEntity.des = des + "搜索字段不传";
        commonBuild(annotationTestEntity, new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());

    }
}
