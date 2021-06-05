package utils.connectdb.mysql;

import lombok.Getter;
import utils.set.PropertiesUtil;

//记录连接数据库的地址，账户，密码在properties中的key
public enum ConnectEnum {
    Force("crm", "crm_dbUrl", "crm_dbAccount", "crm_dbPwd");

    ConnectEnum(String name, String dbUrlKey, String accountKey, String pwdKey) {
        this.name = name;
        this.dbUrlKey = dbUrlKey;
        this.accountKey = accountKey;
        this.pwdKey = pwdKey;
    }

    //填写项目中文名称
    private final String name;
    //数据库地址在properties中对应的key
    private final String dbUrlKey;
    //账户名在properties中对应的key
    private final String accountKey;
    //密码在properties中对应的key
    private final String pwdKey;

    public String getName() {
        return name;
    }

    public String getDbUrl() {
        return PropertiesUtil.get(dbUrlKey);
    }

    public String getAccount() {
        return PropertiesUtil.get(accountKey);
    }

    public String getPwd() {
        return PropertiesUtil.get(pwdKey);
    }
}
