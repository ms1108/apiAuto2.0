package annotation.impl;

import annotation.AnnotationServer;
import annotation.AnnotationTestEntity;
import annotation.IAnnotationTestMethod;
import annotation.annotations.SpecialCharacters;
import config.asserts.ListSearchAssert;
import lombok.SneakyThrows;
import utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SpecialCharacterDefault extends IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(AnnotationTestEntity annotationTestEntity) {
        SpecialCharacters annotation = (SpecialCharacters) annotationTestEntity.annotation;
        String des =
                "类名:" + annotationTestEntity.baseCaseClass.getSimpleName() +
                        ",字段名:" + annotationTestEntity.field.getName() +
                        ",特殊字符测试,";
        String value = "";
        if (StringUtil.isEmpty(annotation.allowCharacters()) && StringUtil.isEmpty(annotation.denyCharacters())) {
            //当未设置允许字符拒绝字符数据时，默认允许全部的特殊字符
            value = annotation.specialCharacter();
            annotationTestEntity.value = value;
            annotationTestEntity.des = des + "允许字符";
            commonBuild(annotationTestEntity, annotation.assertsSuccess().newInstance(), annotation.resetAssert());
        } else if (StringUtil.isNotEmpty(annotation.allowCharacters())) {
            //当设置了允许字符，输入该字符期望成功
            value = annotation.allowCharacters();
            annotationTestEntity.value = value;
            annotationTestEntity.des = des + "允许字符";
            commonBuild(annotationTestEntity, annotation.assertsSuccess().newInstance(), annotation.resetAssert());

            //当设置有拒绝字符时，输入该字符期望失败
            if (StringUtil.isNotEmpty(annotation.denyCharacters())) {
                value = annotation.denyCharacters();
                annotationTestEntity.value = value;
                annotationTestEntity.des = des + "拒绝字符";
                commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

            } else {
                //specialCharacter包含有允许以外的字符，输入该字符期望失败
                value = annotation.specialCharacter();
                annotationTestEntity.value = value;
                annotationTestEntity.des = des + "拒绝字符";
                commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());
            }
        } else if (StringUtil.isEmpty(annotation.allowCharacters()) && StringUtil.isNotEmpty(annotation.denyCharacters())) {
            //当设置了拒绝字符，输入该字符期望失败。
            value = annotation.denyCharacters();
            annotationTestEntity.value = value;
            annotationTestEntity.des = des + "拒绝字符";
            commonBuild(annotationTestEntity, annotation.assertFail().newInstance(), annotation.resetAssert());

            //specialCharacter中删除被拒绝的字符，输入其他字符期望成功
            value = annotation.specialCharacter();
            for (char deny : annotation.denyCharacters().toCharArray()) {
                value = value.replace(deny + "", "");
            }
            annotationTestEntity.value = value;
            annotationTestEntity.des = des + "允许字符";
            commonBuild(annotationTestEntity, annotation.assertsSuccess().newInstance(), annotation.resetAssert());

        }
    }
}
