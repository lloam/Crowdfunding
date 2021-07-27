package com.mao.crowd.service.api;

import com.mao.crowd.entity.vo.DetailProjectVO;
import com.mao.crowd.entity.vo.PortalTypeVO;
import com.mao.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/22 15:49
 * Description:
 */
public interface ProjectPOProviderService {
    void saveProjectPO(ProjectVO projectVO, Integer memberId);


    List<PortalTypeVO> getPortalTypeVOList();

    DetailProjectVO getDetailProject(Integer projectId);
}
