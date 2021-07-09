package api;

import base.ApiMethod;
import base.BaseCase;
import base.IApi;
import com.alibaba.fastjson.JSONObject;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleBlankImpl;
import config.invokerequest.DefaultInvokeRequest;
import config.invokerequest.InvokeRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.testng.Assert;

import java.util.Map;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RequestData {

    private String host;

    private String url;
    //请求的方式
    private ApiMethod methodAndRequestType;
    //jsonSchema的位置
    private String jsonSchemaPath;
    //当步骤描述为空时，使用地址枚举中的描述
    private String des;
    //请求步骤描述
    private String stepDes;
    //cookie
    private Map<String, String> cookies;
    //请求头接口
    private IHeaders iHeaders;
    //请求头
    private Map<String, Object> header;
    //经过参数前置处理器处理的请求参数
    private String param;
    //未经过参数前置处理器处理的请求参数,将会存储到BaseData的map中
    private String notPreHandleParamData;
    //拼接在请求路径后的数据
    private String pathParam;
    //case对象
    private BaseCase baseParam;
    //uri枚举对象
    private IApi iApi;
    //是否开启断言
    private boolean isOpenAssert = true;
    //请求休眠
    private Integer sleep;
    //断言方式
    private AssertMethod assertMethod;
    //幂等请求线程个数
    private int multiThreadNum;
    //用于区分普通请求和幂等请求
    private InvokeRequest invokeRequest = new DefaultInvokeRequest();
    //用于拼接默认的请求字段，比如 {a:{k:v}},a对象每个请求都有
    private IParamPreHandle iParamPreHandle;

    public RequestData(BaseCase param) {
        requestData(param);
    }

    public void requestData(BaseCase param) {
        this.baseParam = param;
        this.iApi = param.getIApi();
        this.host = param.getIApi().getHost();
        this.methodAndRequestType = param.getIApi().getMethodAndRequestType();
        this.jsonSchemaPath = param.getIApi().getJsonSchemaPath();
        this.des = param.getIApi().getDes();
        this.url = param.getIApi().getApiPath();
        this.assertMethod = param.getAssertMethod();
        this.iHeaders = param.getHeaders();//header最好由BaseCase对象传入，因为如果通过RequestData类set进来，其他方法调用该接口时又要set一遍，而且编写代码的人离职后，其他人通过BaseCase更直观的看出
        this.pathParam = param.getPathParam();
        this.iParamPreHandle = param.getIParamPreHandle() != null ? param.getIParamPreHandle() : new ParamPreHandleBlankImpl();
        //param转json串时，以下的字段不需要
        param.iApi = null;
        param.assertMethod = null;
        param.headers = null;
        param.pathParam = null;
        param.iParamPreHandle = null;
        this.notPreHandleParamData = JSONObject.toJSONString(param);
        if (notPreHandleParamData.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("Case类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }
        //置null后避免空指针问题，重新赋值
        param.iApi = this.iApi;
        param.assertMethod = this.assertMethod;
        param.headers = this.iHeaders;
        param.pathParam = this.pathParam;
        param.iParamPreHandle = this.iParamPreHandle;
    }

    public RequestData fail() {
        assertMethod = new FailAssetDefault();
        return this;
    }

    public RequestData offDefaultAssert() {
        isOpenAssert = false;
        return this;
    }

    public String getParam() {
        //之后再调直接返回。
        // 传入notPreHandleParamData是因为注解测试会对数据做修改，正在发送数据是通过调getParam获取请求数据
        return this.param == null ? this.iParamPreHandle.paramPreHandle(notPreHandleParamData) : this.param;
    }

    public Map<String, Object> getHeader() {
        //其他地方可以通过getHeader获取请求头，再通过set进来，实现对请求头的修改
        return this.header == null ? this.iHeaders.getHeaders(this) : this.header;
    }
}
