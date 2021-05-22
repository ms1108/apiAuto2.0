package utils;

import base.BaseCase;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ClassFinderUtil {

    //{包名:[类名]}
    @SneakyThrows
    public Map<String, List<String>> scanPackage(String scannedPackage) {
        String scannedPath = scannedPackage.replace('.', '/');
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. Are you sure the package '%s' exists?", scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        Map<String, List<String>> packageAndClassNames = new HashMap<>();
        List<String> classNames = new ArrayList<>();
        for (File file : Objects.requireNonNull(scannedDir.listFiles())) {
            String fileName = file.getName();
            //不包含$符，即排除内部类
            if (fileName.endsWith(".class") && !fileName.contains("$")) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                classNames.add(className);
            }
            //子包
            if (file.isDirectory()) {
                packageAndClassNames.putAll(scanPackage(scannedPackage + "." + file.getName()));
            }
        }
        if (classNames.size() > 0)
            packageAndClassNames.put(scannedPackage, classNames);
        return packageAndClassNames;
    }

    //获取case类
    public List<Class<? extends BaseCase>> scanBaseCaseClass(String scannedPackage) {
        List<Class<? extends BaseCase>> classes = new ArrayList<>();
        Map<String, List<String>> packageAndClassNames = scanPackage(scannedPackage);
        for(Map.Entry<String, List<String>> entry : packageAndClassNames.entrySet()){
            //继承了BaseCase的类都在testcase包下
            if (!entry.getKey().contains("testcase")) {
                continue;
            }
            for (String className : entry.getValue()) {
                String resource = entry.getKey() + '.' + className;
                try {
                    Class<?> aClass = Class.forName(resource);
                    classes.add((Class<? extends BaseCase>) aClass);
                } catch (Exception e) {
                    ReportUtil.log(resource + "，该类未继承BaseCase");
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        System.out.println(classFinderUtil.scanPackage(""));
        //System.out.println(classFinderUtil.scanned("business.loginTest.testcase"));
    }

}
