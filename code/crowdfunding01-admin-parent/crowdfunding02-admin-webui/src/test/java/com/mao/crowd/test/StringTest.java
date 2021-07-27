package com.mao.crowd.test;

import com.mao.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * Author: Administrator
 * Date: 2021/7/11 20:40
 * Description:
 */
public class StringTest {

    @Test
    public void testMd5() {
        String source = "999999";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }

    @Test
    public void test(){
        method1();
    }
    public void method1() {
        System.out.println("11111111开始");
        method2();
        System.out.println("11111111结束");
    }
    public void method2() {
        System.out.println("22222222开始");
        method3();
        System.out.println("22222222结束");
    }
    public void method3() {
        System.out.println("33333333开始");

        System.out.println("33333333结束");
    }
}
