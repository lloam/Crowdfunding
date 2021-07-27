package com.mao.crowd.controller;

import com.mao.crowd.entity.vo.DetailProjectVO;
import com.mao.crowd.entity.vo.PortalTypeVO;
import com.mao.crowd.entity.vo.ProjectVO;
import com.mao.crowd.mapper.ProjectPOMapper;
import com.mao.crowd.service.api.ProjectPOProviderService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/22 15:48
 * Description: 暴露接口给 feign，消费者能够通过 feign 远程调用这些方法
 */
@RestController
public class ProjectPOProviderController {

    @Autowired
    private ProjectPOProviderService projectPOProviderService;


    /**
     * 保存 projectPO
     * @param projectVO
     * @param memberId
     * @return
     */
    @PostMapping("/save/projectPO/remote")
    ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId) {

        try {
            // 调用本地 Service 执行保存
            projectPOProviderService.saveProjectPO(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * 获取首页项目的类型数据信息
     * @return
     */
    @GetMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {
        try {
            List<PortalTypeVO> portalTypeVOList = projectPOProviderService.getPortalTypeVOList();

            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙，数据获取失败！");
        }
    }


    /**
     * 根据项目 id 获取详细的项目信息
     * @param projectId
     * @return
     */
    @GetMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId) {
        try {
            DetailProjectVO detailProject = projectPOProviderService.getDetailProject(projectId);

            return ResultEntity.successWithData(detailProject);
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed("服务器繁忙，请重试！");
        }

    }
}
