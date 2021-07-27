package com.mao.crowd.mapper;

import com.mao.crowd.entity.po.ProjectPO;
import com.mao.crowd.entity.po.ProjectPOExample;
import com.mao.crowd.entity.vo.DetailProjectVO;
import com.mao.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ProjectPOMapper {
    long countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    void insertTypeRelationship(@Param(("typeIdList")) List<Integer> typeIdList, @Param("projectPOId") Integer projectPOId);

    void insertTagRelationship(@Param("tagIdList") List<Integer> tagIdList, @Param("projectPOId") Integer projectPOId);

    // 查询首页需要展示的项目分类以及项目信息
    List<PortalTypeVO> selectPortalTypeVOList();

    // 根据项目 id 查询到具体的项目信息
    DetailProjectVO selectDetailProjectVO(Integer projectId);
}