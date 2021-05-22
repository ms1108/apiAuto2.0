package annotation;

import annotation.annotations.*;
import api.RequestData;
import base.BaseCase;
import base.DataStore;
import base.CommandLogic;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import config.asserts.AssertMethod;
import lombok.SneakyThrows;
import utils.ClassFinderUtil;
import utils.ReportUtil;
import utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public class AnnotationServer extends CommandLogic {

    private String rootPath = "";
    private String rootFieldName = "";
    private List<String> annotationNames;
    public BaseCase baseCase;


    @SneakyThrows
    public void annotationServer(Class<? extends BaseCase> baseCaseClass, String executeAnnotationAble) {
        if (!DataStore.isOpenAnnotation) {
            ReportUtil.log("------------------------------------------------注解测试已被关闭------------------------------------------------");
            return;
        }
        baseCase = baseCaseClass.newInstance();
        Method[] methods = baseCaseClass.getMethods();
        List<Method> baseCaseData = new ArrayList<>();
        List<Method> autoTestMethod = new ArrayList<>();
        List<Method> multiRequestMethod = new ArrayList<>();
        for (Method method : methods) {
            //DataDepend调用前置接口,只会调用一次
            if (isExecuteMethod(method, DataDepend.class, executeAnnotationAble)) {
                method.invoke(baseCase);
            }
            if (executeAnnotationAble.contains(BaseCaseData.class.getSimpleName())
                    && method.isAnnotationPresent(BaseCaseData.class)) {
                baseCaseData.add(method);
            }
            if (isExecuteMethod(method, AutoTest.class, executeAnnotationAble)) {
                autoTestMethod.add(method);
            }
            if (isExecuteMethod(method, MultiRequest.class, executeAnnotationAble)) {
                multiRequestMethod.add(method);
            }
        }
        for (Method method : baseCaseData) {
            String logMethodName = "------------------------------------------------" + method.getName() + BaseCaseData.class.getSimpleName() + "------------------------------------------------";
            ReportUtil.setPreLog(logMethodName);
            baseCaseField(method, executeAnnotationAble);
            //因为第一个遍历可能不发送接口所以这个logMethodName的预置日志要删掉
            ReportUtil.deleteLog(logMethodName);
        }
        if (executeAnnotationAble.contains(BaseCaseData.class.getSimpleName()) && baseCaseData.size() == 0) {
            ReportUtil.setPreLog("-----------------------------------" + BaseCaseData.class.getSimpleName() + "为" + baseCaseClass.getSimpleName() + "的无参构造方法" + "-----------------------------------");
            baseCaseField(null, executeAnnotationAble);
        }
        for (Method method : autoTestMethod) {
            AutoTest annotation = method.getAnnotation(AutoTest.class);
            BaseCase baseCaseTest = (BaseCase) method.invoke(baseCase);
            String des = "执行@AutoTest,类名:" + baseCase.getClass().getSimpleName() +
                    ",方法名:" + method.getName() + "，" + annotation.des();
            apiTest(new RequestData(baseCaseTest)
                    .setStepDes(des)
                    .setOpenAssert(annotation.isOpenAssert())
                    .setSleep(annotation.sleep()));
        }
        for (Method method : multiRequestMethod) {
            MultiRequest annotation = method.getAnnotation(MultiRequest.class);
            BaseCase baseCaseMethod = (BaseCase) method.invoke(baseCase);
            String des = "执行@MultiRequest,类名:" + baseCase.getClass().getSimpleName() +
                    ",方法名:" + method.getName() + "，" + annotation.des();
            RequestData requestData = new RequestData(baseCaseMethod)
                    .setMultiThreadNum(annotation.multiThreadNum())
                    .setIRequestMethod(annotation.iRequest().newInstance())
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

    private void baseCaseField(Method method, String executeAnnotationAble) {
        //准备一份基础数据baseCaseBackup
        getBaseCaseObject(method);
        Field[] fields = baseCase.getClass().getFields();
        fieldAnnotation(fields, method, executeAnnotationAble);
    }

    @SneakyThrows
    private void fieldAnnotation(Field[] fields, Method method, String executeAnnotationAble) {
        BaseCaseData baseCaseData;
        String group = "0";
        if (method != null) {
            baseCaseData = method.getDeclaredAnnotation(BaseCaseData.class);
            group = baseCaseData.group();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (isExecuteField(field, NotNull.class, executeAnnotationAble)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, NotEmpty.class, executeAnnotationAble)) {
                NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }

            if (isExecuteField(field, Unique.class, executeAnnotationAble)) {
                Unique annotation = field.getAnnotation(Unique.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, Length.class, executeAnnotationAble)) {
                Length annotation = field.getAnnotation(Length.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, Range.class, executeAnnotationAble)) {
                Range annotation = field.getAnnotation(Range.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    //自定义注解中的测试流程，示例
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, StringToInt.class, executeAnnotationAble)) {
                StringToInt annotation = field.getAnnotation(StringToInt.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, IntToString.class, executeAnnotationAble)) {
                IntToString annotation = field.getAnnotation(IntToString.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, Search.class, executeAnnotationAble)) {
                Search annotation = field.getAnnotation(Search.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, Chinese.class, executeAnnotationAble)) {
                Chinese annotation = field.getAnnotation(Chinese.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, Blank.class, executeAnnotationAble)) {
                Blank annotation = field.getAnnotation(Blank.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, SpecialCharacters.class, executeAnnotationAble)) {
                SpecialCharacters annotation = field.getAnnotation(SpecialCharacters.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, EnumInt.class, executeAnnotationAble)) {
                EnumInt annotation = field.getAnnotation(EnumInt.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (isExecuteField(field, EnumString.class, executeAnnotationAble)) {
                EnumString annotation = field.getAnnotation(EnumString.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            //getType()含有$说明含有内部类
            if (field.getType().toString().contains("$")) {
                rootPath = rootPath + field.getName() + ".";
                inertClass(method, baseCase, field.getType().getSimpleName(), executeAnnotationAble);
            }
            rootPath = "";
        }
    }

    @SneakyThrows
    public void fieldTest(Method method, Field field, Object value, String des, AssertMethod assertMethod, String resetAssert) {
        BaseCase baseCaseMethod = getBaseCaseObject(method);
        RequestData requestData = new RequestData(baseCaseMethod);
        baseCase = baseCase.getClass().newInstance();//因为走了RequestData，serverMap会被置空，所以再new一遍
        String targetPath = rootPath + field.getName();
        requestData.setParamData(replaceValue(requestData.getParamData(), targetPath, value));
        requestData.setStepDes(des);
        if (StringUtil.isNotEmpty(resetAssert)) {
            AssertMethod retAssertMethod = (AssertMethod) baseCaseMethod.getClass().getMethod(resetAssert).invoke(baseCaseMethod);
            requestData.setAssertMethod(retAssertMethod);
        } else {
            requestData.setAssertMethod(assertMethod);
        }

        apiTest(requestData);

    }

    private void inertClass(Method method, BaseCase baseCase, String className, String executeAnnotationAble) {
        Class<?>[] innerClazz = baseCase.getClass().getDeclaredClasses();
        for (Class claszInner : innerClazz) {
            if (className.equals(claszInner.getSimpleName())) {
                Field[] fields = claszInner.getDeclaredFields();
                fieldAnnotation(fields, method, executeAnnotationAble);
            }
        }
    }

    @SneakyThrows
    private boolean isExecuteMethod(Method method, Class<? extends Annotation> annotation, String executeAnnotationAble) {
        //先判断是否符合executeAnnotationAble的要求
        if (method.isAnnotationPresent(annotation)
                && executeAnnotationAble.contains(annotation.getSimpleName())
                && executeAnnotationAble.contains(method.getName())) {
            return true;
        } else if (method.isAnnotationPresent(annotation)
                && annotation.getSimpleName().equals(DataDepend.class.getSimpleName())) {
            //如果当前是DataDepend注解，则获取方法注解信息，该信息定义了是否在每个字段注解测试时都执行一遍DataDepend标记的方法
            DataDepend dataDepend = method.getAnnotation(DataDepend.class);
            return dataDepend.value();
        }
        return false;
    }

    private boolean isExecuteField(Field field, Class<? extends Annotation> annotation, String executeAnnotationAble) {
        return field.isAnnotationPresent(annotation)
                && executeAnnotationAble.contains(annotation.getSimpleName())
                && (executeAnnotationAble.split(",")[0].contains(".")//含有点说明该字段为内部类的字段
                ? executeAnnotationAble.split(",")[0].endsWith(field.getName())
                : executeAnnotationAble.split(",")[0].contains(field.getName()))
                ;
    }

    @SneakyThrows
    public BaseCase getBaseCaseObject(Method method) {
        BaseCase baseCaseObject;
        if (method != null) {
            baseCaseObject = (BaseCase) method.invoke(baseCase);
        } else {
            baseCaseObject = baseCase.getClass().getConstructor().newInstance();
        }
        return baseCaseObject;
    }

    private String replaceValue(String param, String targetPath, Object value) {
        JSONObject jsonObj = JSON.parseObject(param);
        JSONPath.set(jsonObj, targetPath, value);
        return JSON.toJSONString(jsonObj);
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public List<Class<? extends BaseCase>> getBaseCaseName(String scannedPackage) {
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        return classFinderUtil.scanBaseCaseClass(scannedPackage);
    }

    /**
     * 获取自定义的所有注解名称
     *
     * @return
     */
    public List<String> getAnnotationName() {
        if (annotationNames == null) {
            ClassFinderUtil classFinderUtil = new ClassFinderUtil();
            Collection<List<String>> values = classFinderUtil.scanPackage("annotation.annotations").values();
            List<String> annotationNames = new ArrayList<>();
            for (List<String> value : values) {
                annotationNames.addAll(value);
            }
            return annotationNames;
        } else {
            return this.annotationNames;
        }
    }

    /**
     * 获取方法名称和修饰该方法的所有注解名称
     *
     * @param baseCase
     * @return
     */
    public Map<String, List<String>> getMethodNameAndAnnotationName(Class<? extends BaseCase> baseCase) {
        Map<String, List<String>> methodNameAndAnnotationNames = new HashMap<>();
        //用于排除非annotations包下的注解
        List<String> aNames = getAnnotationName();
        for (Method method : baseCase.getDeclaredMethods()) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            List<String> annotationNames = new ArrayList<>();
            for (Annotation annotation : declaredAnnotations) {
                String annotationName = annotation.annotationType().getSimpleName();
                //当前注解在自定义注解的列表中，则添加
                if (aNames.contains(annotationName)) {
                    annotationNames.add(annotationName);
                }
            }
            //判断该方法上有没有自定义注解，目的不往Map中放空列表
            if (annotationNames.size() != 0) {
                //将该方法及方法上修饰的自定义注解添加进map中
                methodNameAndAnnotationNames.put(method.getName(), annotationNames);
            }

        }
        return methodNameAndAnnotationNames;
    }

    @SneakyThrows
    public Map<String, List<String>> getFieldNameAndAnnotationName(Class<? extends BaseCase> baseCaseClass) {
        Map<String, List<String>> fieldNameAndAnnotationName = new HashMap<>();
        Field[] fields = baseCaseClass.getFields();
        return getFieldNameAndAnnotationName(baseCaseClass.newInstance(), fields, fieldNameAndAnnotationName);
    }

    /**
     * 获取字段名和注解的名称
     *
     * @param baseCase
     * @param fields
     * @param fieldNameAndAnnotationName
     * @return
     */
    public Map<String, List<String>> getFieldNameAndAnnotationName(BaseCase baseCase, Field[] fields, Map<String, List<String>> fieldNameAndAnnotationName) {
        //用于排除非annotations包下的注解
        List<String> aNames = getAnnotationName();
        for (Field field : fields) {
            List<String> annotationNames = new ArrayList<>();
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                String annotationName = annotation.annotationType().getSimpleName();
                //当前注解在自定义注解的列表中，则添加
                if (aNames.contains(annotationName)) {
                    annotationNames.add(annotationName);
                }
            }
            //判断该字段上有没有自定义注解，目的不往Map中放空列表
            if (annotationNames.size() != 0) {
                //将该字段及方法上修饰的自定义注解添加进map中
                fieldNameAndAnnotationName.put(rootFieldName + field.getName(), annotationNames);
            }
            //处理内部类
            if (field.getType().toString().contains("$")) {
                rootFieldName = rootFieldName + field.getName() + ".";
                getInertClassFieldName(baseCase, field.getType().getSimpleName(), fieldNameAndAnnotationName);
            }
            rootFieldName = "";
        }
        return fieldNameAndAnnotationName;
    }


    private void getInertClassFieldName(BaseCase baseCase, String className, Map<String, List<String>> fieldNameAndAnnotationName) {
        Class<?>[] innerClazz = baseCase.getClass().getDeclaredClasses();
        for (Class claszInner : innerClazz) {
            if (className.equals(claszInner.getSimpleName())) {
                //获取内部类字段
                Field[] fields = claszInner.getDeclaredFields();
                getFieldNameAndAnnotationName(baseCase, fields, fieldNameAndAnnotationName);
            }
        }
    }
}