package com.mao.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: Administrator
 * Date: 2021/7/21 11:21
 * Description: 发送短信验证码的 host 等属性,更换不同的厂商这些属性是不同的,不能写死在代码中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {

    private String host;

    private String path;

    private String method;

    private String appCode;

    private String smsSignId;

    private String templateId;
}
