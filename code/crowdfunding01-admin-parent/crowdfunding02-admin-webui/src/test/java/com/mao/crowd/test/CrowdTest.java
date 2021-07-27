package com.mao.crowd.test;

import com.mao.crowd.entity.Admin;
import com.mao.crowd.entity.Role;
import com.mao.crowd.mapper.AdminMapper;
import com.mao.crowd.mapper.RoleMapper;
import com.mao.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Administrator
 * Date: 2021/7/10 23:40
 * Description:
 */
// 在类上标记必要的注解，Spring 整合 Junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMapper(){
        Admin admin = new Admin(null, "lloam", "123123", "汤姆", "2196143404@qq.com", null);
        int count = adminMapper.insert(admin);
        /**
         * 如果在实际开发中，所有想查看数值的地方都使用 sout 方式打印，会给项目上线运行带来问题！
         * sout 本质上是一个 IO 操作，通常 IO 操作是比较消耗性能的。如果项目中 sout 很多，那么对性能的影响就比较大了
         * 即使上线前专门花时间删除代码中的 sout，也很可能有遗漏，而且非常麻烦。
         * 而且如果使用日志系统，那么通过日志级别就可以批量的控制信息的打印
         */
        System.out.println("受影响的行数：" + count);
    }

    @Test
    public void testLog() {
        // 1、获取 Logger 对象
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);

        // 2、根据不同日志级别打印日志
        logger.debug("Hello,I'm Debug level!!!");
        logger.debug("Hello,I'm Debug level!!!");
        logger.debug("Hello,I'm Debug level!!!");

        logger.info("Info level!!!");
        logger.info("Info level!!!");
        logger.info("Info level!!!");

        logger.warn("Warn level!!!");
        logger.warn("Warn level!!!");
        logger.warn("Warn level!!!");

        logger.error("Error level!!!");
        logger.error("Error level!!!");
        logger.error("Error level!!!");
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testAddAdmin() {
        for (int i = 0; i < 238; i++) {
            adminMapper.insert(new Admin(null,"logigAcct" + i,"userPswd" + i,"userName" + i,"email" + i,null));
        }
    }

    @Test
    public void testRoleSave() {
        for (int i = 0; i < 235; i++) {
            roleMapper.insert(new Role(null,"role" + i));
        }
    }
}
