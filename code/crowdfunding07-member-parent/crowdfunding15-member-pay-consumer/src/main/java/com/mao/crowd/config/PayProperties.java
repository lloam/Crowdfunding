package com.mao.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Administrator
 * Date: 2021/7/27 16:04
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "ali.pay")
public class PayProperties {

    // appId
    private String appId;

    // 用户私钥
    private String merchantPrivateKey;

    // 支付宝公钥
    private String alipayPublicKey;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 工程公网访问地址使用内网穿透客户端提供的域名
    private String notifyUrl;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 工程公网访问地址使用内网穿透客户端提供的域名
    private String returnUrl;

    // 签名方式
    private String signType;

    // 字符集
    private String charset;

    // 支付宝网关（正式环境）
    // public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    // 支付宝网关（沙箱环境）
    private String gatewayUrl;

}
