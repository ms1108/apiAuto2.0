package utils.set;

import utils.StringUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
    //需要加载的配置文件
    public static String env = "test";

    //资源参数
    private static Properties props;

    static {
        String systemEnv = System.getProperty("env");
        if (StringUtil.isNotEmpty(systemEnv)) {
            PropertiesUtil.env = systemEnv;
        }
        props = new Properties();
        try {
            //读取propertiesUtil类的配置
            //利用反射加载类信息，获取配置文件的文件流，并指点编码格式
            //当该类与properties不在同级时需要加/
            props.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/resource-" + PropertiesUtil.env + ".properties"), "UTF-8"));
            //添加数据库的配置文件,如果key有冲突则覆盖
            Properties propsDataBase = new Properties();
            propsDataBase.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/resource-" + PropertiesUtil.env + "-database.properties"), "UTF-8"));
            props.putAll(propsDataBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将mvn传进来的参数加载到Properties中
        MvnArgsUtil mvnArgs = new MvnArgsUtil();
        mvnArgs.mvnArgs();
    }

    public static void set(String key, String value) {
        props.setProperty(key, value);
    }

    public static String get(String key) {
        String value = props.getProperty(key.trim(), "").trim();
        //防止在配置中写了引号
        if (value.startsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

}
