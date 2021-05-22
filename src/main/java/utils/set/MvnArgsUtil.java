package utils.set;

import base.DataStore;
import utils.StringUtil;

public class MvnArgsUtil {
    //目的将字符串用变量表示
    public final static String g_host = "g_host";
    public final static String g_loginName = "g_loginName";
    public final static String g_loginPwd = "g_loginPwd";
    public final static String is_open_annotation = "is_open_annotation";
    public final static String email_recipients = "email_recipients";
    public final static String data_factory_list_api = "data_factory_list_api";

    public void mvnArgs() {
        String host = System.getProperty(g_host);
        String loginName = System.getProperty(g_loginName);
        String loginPwd = System.getProperty(g_loginPwd);
        String isOpenAnnotation = System.getProperty(is_open_annotation);
        String emailRecipients = System.getProperty(email_recipients);
        String dataFactoryListApi = System.getProperty(data_factory_list_api);

        if (!StringUtil.isEmpty(host)) {
            PropertiesUtil.set(g_host, host);
        }
        if (!StringUtil.isEmpty(loginName)) {
            PropertiesUtil.set(g_loginName, loginName);
        }
        if (!StringUtil.isEmpty(loginPwd)) {
            PropertiesUtil.set(g_loginPwd, loginPwd);
        }
        if (!StringUtil.isEmpty(isOpenAnnotation)) {
            DataStore.isOpenAnnotation = "true".equals(isOpenAnnotation);
        }
        if (!StringUtil.isEmpty(emailRecipients)) {
            PropertiesUtil.set(email_recipients, emailRecipients);
        }
        if (!StringUtil.isEmpty(dataFactoryListApi)) {
            PropertiesUtil.set(data_factory_list_api, dataFactoryListApi);
        }
    }
}
