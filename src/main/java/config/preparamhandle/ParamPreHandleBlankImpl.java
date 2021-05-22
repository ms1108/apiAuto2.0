package config.preparamhandle;

import com.alibaba.fastjson.JSONObject;

public class ParamPreHandleBlankImpl implements IParamPreHandle {
    @Override
    public String paramPreHandle(String param) {
        return param;
    }

    @Override
    public String paramPathPreHandle(String param) {
        return param;
    }
}
