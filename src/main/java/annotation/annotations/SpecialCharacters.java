package annotation.annotations;

import annotation.IAnnotationTestMethod;
import annotation.impl.SpecialCharacterDefaultImpl;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface SpecialCharacters {
    String specialCharacter() default "`~!@#$%^&*()_-+=|\\{}[]:;\"'<>?,./，。、（）？￥’”\\n\\r\\t\\b\\f";

    String allowCharacters() default "";

    String denyCharacters() default "";

    Class<? extends AssertMethod> assertsSuccess() default SuccessAssertDefault.class;

    Class<? extends AssertMethod> assertFail() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    Class<? extends IAnnotationTestMethod> testMethod() default SpecialCharacterDefaultImpl.class;
}
