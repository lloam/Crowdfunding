package com.mao.crowd.test;

import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.entity.vo.DetailProjectVO;
import com.mao.crowd.entity.vo.PortalTypeVO;
import com.mao.crowd.mapper.MemberPOMapper;
import com.mao.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/20 18:49
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    private Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testConnection() throws SQLException {

        Connection connection = dataSource.getConnection();

        logger.debug(connection.toString());
    }


    @Test
    public void  testMapper() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String source = "123123";

        String encode = passwordEncoder.encode(source);

        MemberPO memberPO = new MemberPO(null,"jack",encode,"杰克","jack@qq.com",1,1,"杰克","123123",2);

        memberPOMapper.insert(memberPO);
    }

    /**
     * 测试首页查询项目信息
     */
    @Test
    public void testLoadTypeData() {
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : portalTypeVOList) {
            logger.debug(portalTypeVO.toString());
        }
    }

    /**
     * 测试获取详细项目信息
     */
    @Test
    public void testGetDetailProject() {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(15);
        logger.debug(detailProjectVO.toString());
    }
}
