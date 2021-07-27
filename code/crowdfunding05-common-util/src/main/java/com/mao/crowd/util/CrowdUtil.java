package com.mao.crowd.util;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.mao.crowd.constant.CrowdConstant;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author: Administrator
 * Date: 2021/7/11 17:15
 * Description: 尚筹网通用的工具方法类
 */
public class CrowdUtil {

    /**
     * 判断当前请求是否为 Ajax 请求
     * @param request 请求对象
     * @return
     *      true：当前请求是 Ajax 请求
     *      false：当前请求不是 Ajax 请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 1、获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        // 2、判断
        return (acceptHeader != null && acceptHeader.equals("application/json")) ||
                (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }


    /**
     * 对明文字符串进行 MD5 加密
     * @param source    传入的明文字符串
     * @return
     */
    public static String md5(String source) {

        // 1、判断 source 是否有效
        if(source == null || source.length() == 0) {
            // 2、如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 3、获取一个 MessageDigest 对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4、获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            // 5、执行加密
            byte[] output = messageDigest.digest(input);

            // 6、创建 BigInteger 对象
            int signum = 1; // 生成的数字的正负性
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7、按照 16 进制将 bigInteger 的值转换为字符串
            int radix = 16; // 字符串表示的基数
            String encoded = bigInteger.toString(radix).toUpperCase();

            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param host  短信接口调用的 URL 地址
     * @param path  具体发送短信功能的地址
     * @param method    请求方式
     * @param phoneNum  接收验证码的手机号
     * @param code      验证码
     * @param appCode   用来调用第三方短信 API 的 AppCode
     * @param smsSignId 签名编号
     * @param templateId    模板编号
     * @return
     */
    public static ResultEntity<String> sendCodeByShortMessage(

            String host,

            String path,

            String method,

            String phoneNum,

            String code,

            String appCode,

            String smsSignId,

            String templateId) {


        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);

        // 封装其他的参数
        Map<String, String> querys = new HashMap<String, String>();

        // 收短信的手机号
        querys.put("mobile", phoneNum);

        // 要发送的验证码，也就是模板中会变化的部分
        querys.put("param", "**code**:"+code+",**minute**:5");

        // 签名编号
        querys.put("smsSignId", smsSignId);

        // 模板编号
        querys.put("templateId", templateId);
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

            // 状态码：200正常；400 URL 无效；401 appCode 错误；403 次数用完；500 API 网关错误
            StatusLine statusLine = response.getStatusLine();
            // 响应状态码
            int statusCode = statusLine.getStatusCode();

            // 状态或出错原因
            String reasonPhrase = statusLine.getReasonPhrase();

            if(statusCode == 200) {
                return ResultEntity.successWithData(code);
            }

            return ResultEntity.failed(reasonPhrase);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
