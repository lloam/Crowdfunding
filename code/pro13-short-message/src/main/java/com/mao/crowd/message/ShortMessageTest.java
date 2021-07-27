package com.mao.crowd.message;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Administrator
 * Date: 2021/7/21 7:51
 * Description:
 */
public class ShortMessageTest {

    public static void main(String[] args) {

        // 短信接口调用的 URL 地址
        String host = "https://gyytz.market.alicloudapi.com";

        // 具体发送短信功能的地址
        String path = "/sms/smsSend";

        // 请求方式
        String method = "POST";

        // 登录到 阿里云 进入控制台找到已购买的短信接口的 appcode
        String appcode = "98a7b521b3af411eba4f88635372c856";
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);

        // 封装其他的参数
        Map<String, String> querys = new HashMap<String, String>();

        // 收短信的手机号
        querys.put("mobile", "18370977271");

        // 要发送的验证码，也就是模板中会变化的部分
        querys.put("param", "**code**:12345,**minute**:5");

        // 签名编号
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");

        // 模板编号
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            // 获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
