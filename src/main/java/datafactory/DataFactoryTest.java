package datafactory;

import annotation.annotations.DataDepend;
import api.ApiTest;
import api.RequestData;
import base.BaseCase;
import datafactory.annotation.DataFactory;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import utils.ClassFinderUtil;
import utils.ReportUtil;
import utils.set.PropertiesUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.set.MvnArgsUtil.data_factory_list_api;

public class DataFactoryTest extends ApiTest {
    public List<DataFactoryEntity> dataFactoryBaseCase;

    @Test
    @SneakyThrows
    public void createData() {
        if (dataFactoryBaseCase == null) {
            dataFactoryBaseCase = getDataFactoryBaseCase("component");
        }
        //获取符合条件的实体
        List<DataFactoryEntity> dataFactoryEntities = dataFactoryBaseCase.stream().filter(d -> {
            DataFactory annotationInfo = (DataFactory) d.getAnnotationInfo();
            try {
                String listApi = PropertiesUtil.get(data_factory_list_api);
                if (listApi.endsWith("/")) {
                    listApi = listApi.substring(0, listApi.length() - 1);
                }
                return annotationInfo.listApi().newInstance().serverMap.getUri().contains(listApi);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        //发送接口
        for (DataFactoryEntity dataFactoryEntity : dataFactoryEntities) {
            BaseCase baseCase = dataFactoryEntity.getBaseCase().newInstance();
            //调用依赖方法，创建数据
            dataFactoryEntity.getDependMethod().invoke(baseCase);
            //调用创造数据方法
            DataFactory annotation = dataFactoryEntity.getDataFactoryMethod().getAnnotation(DataFactory.class);
            ReportUtil.log("DataFactoryDes    : " + annotation.des());
            BaseCase dataFactoryBaseCase = (BaseCase) dataFactoryEntity.getDataFactoryMethod().invoke(baseCase);
            apiTest(new RequestData(dataFactoryBaseCase));
        }

    }

    //扫描包并存储满足条件的类和方法
    @SneakyThrows
    public List<DataFactoryEntity> getDataFactoryBaseCase(String packageName) {
        List<DataFactoryEntity> dataFactoryEntities = new ArrayList<>();
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        List<Class<? extends BaseCase>> classes = classFinderUtil.scanBaseCaseClass(packageName);
        for (Class<? extends BaseCase> aClass : classes) {
            Method dependMethod = null;
            List<Method> dataFactoryMethods = new ArrayList<>();
            //先获取该类下所有符合条件的方法
            for (Method method : aClass.getMethods()) {
                if (method.isAnnotationPresent(DataDepend.class)) {
                    dependMethod = method;
                }
                if (method.isAnnotationPresent(DataFactory.class)) {
                    dataFactoryMethods.add(method);
                }
            }
            //对符合条件的方法进入列表
            for (Method dataFactoryMethod : dataFactoryMethods) {
                DataFactoryEntity dataFactoryEntity = new DataFactoryEntity();
                dataFactoryEntity.setBaseCase(aClass);
                dataFactoryEntity.setDependMethod(dependMethod);
                dataFactoryEntity.setDataFactoryMethod(dataFactoryMethod);
                dataFactoryEntity.setAnnotationInfo(dataFactoryMethod.getAnnotation(DataFactory.class));
                dataFactoryEntities.add(dataFactoryEntity);
            }
        }
        return dataFactoryEntities;
    }
}
