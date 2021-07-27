package com.mao.crowd.service.Impl;

import com.mao.crowd.entity.po.MemberConfirmInfoPO;
import com.mao.crowd.entity.po.MemberLaunchInfoPO;
import com.mao.crowd.entity.po.ProjectPO;
import com.mao.crowd.entity.po.ReturnPO;
import com.mao.crowd.entity.vo.*;
import com.mao.crowd.mapper.*;
import com.mao.crowd.service.api.ProjectPOProviderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Administrator
 * Date: 2021/7/22 15:49
 * Description:
 */
@Transactional(readOnly = true)
@Service
public class ProjectPOProviderServiceImpl implements ProjectPOProviderService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;


    /**
     * 保存 projectPO,
     * 保存项目、分类的关联关系信息
     * 保存项目、标签的关联关系信息
     * 保存项目中详情图片的路径信息
     * 保存项目发起人信息
     * 保存项目回报信息
     * 保存项目确认信息
     * @param projectVO
     * @param memberId
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProjectPO(ProjectVO projectVO, Integer memberId) {

        // 一、保存 ProjectPO 对象
        // 1、创建空的 ProjectPO 对象
        ProjectPO projectPO = new ProjectPO();

        // 2、把 projectVO 中的属性复制到 projectPO 中
        BeanUtils.copyProperties(projectVO, projectPO);

        // 修复 bug：需要把 memberId 设置到 projectPO 对象中
        projectPO.setMemberid(memberId);

        // 修复 bug：生成创建时间
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createdate);

        // 修复 bug：status 设置成 0，表示即将开始
        projectPO.setStatus(0);

        // 3、保存 projectPO
        // 为了能够获取 projectPO 保存后的自增主键，需要到 ProjectPOMapper.xml 文件中进行相关的设置
        // <insert id="insertSelective" useGeneratedKeys="true" keyProperty="id" .....
        projectPOMapper.insertSelective(projectPO);

        // 4、从 projectPO 对象中获取自增的主键，用于保存
        Integer projectPOId = projectPO.getId();

        // 二、保存项目、分类的关联关系信息
        // 1、从 projectVO 对象中获取 typeIdList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList, projectPOId);

        // 三、保存项目、标签的关联关系信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList, projectPOId);

        // 四、保存项目中详情图片的路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertPathList(projectPOId, detailPicturePathList);

        // 五、保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);

        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        // 六、保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

        List<ReturnPO> returnPOList = new ArrayList<>();

        ReturnPO returnPO;

        for (ReturnVO returnVO : returnVOList) {

            returnPO = new ReturnPO();

            BeanUtils.copyProperties(returnVO, returnPO);

            returnPO.setProjectid(projectPOId);

            returnPOList.add(returnPO);
        }

        returnPOMapper.insertReturnPOBatch(returnPOList);

        // 七、保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
    }

    /**
     * 获取首页显示的项目信息
     * @return
     */
    public List<PortalTypeVO> getPortalTypeVOList() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    /**
     * 根据项目 id 获取详细的项目信息
     * @param projectId 项目 id
     * @return
     */
    public DetailProjectVO getDetailProject(Integer projectId) {

        // 1、根据 projectId 查询对应的项目 project，封装成 DetailProjectVO
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);

        // 2、设置项目当前状态
        Integer status = detailProjectVO.getStatus();
        switch (status){
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }

        // 3、根据 deployDate 计算 lastDay
        // 2021-7-23
        String deployDate = detailProjectVO.getDeployDate();

        // 获取当前日期
        Date currentDay = new Date();

        // 把众筹日期解析成 Date 类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 将项目的发布日期转换成时间戳
            Date deployDay = format.parse(deployDate);

            // 获取当前日期的时间戳
            long currentTimeStamp = currentDay.getTime();

            // 获取众筹日期的时间戳
            long deployTimeStamp = deployDay.getTime();

            // 两个时间戳相减计算当前已经过去的时间
            long pastDays = (currentTimeStamp - deployTimeStamp) / 1000 / 60 / 60 / 24;

            // 使用总的众筹天数减去已经过去的天数
            Integer totalDays = detailProjectVO.getDay();

            // 得到众筹项目还剩余的天数
            Integer lastDay = (int)(totalDays - pastDays);

            // 将剩余天数设置进 detailProjectVO
            detailProjectVO.setLastDay(lastDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        // 4、设置回报信息的支持者数量，这里只给定一个随机数
//        Integer proSupporterCount = detailProjectVO.getSupporterCount();
//
//        // 支持数应小于项目的支持数
//        Random random = new Random(proSupporterCount);
//
//        int returnSupportCount = random.nextInt();
//
//        List<DetailReturnVO> detailReturnVOList = detailProjectVO.getDetailReturnVOList();
//
//        for (DetailReturnVO detailReturnVO : detailReturnVOList) {
//            detailProjectVO.setSupporterCount(returnSupportCount);
//        }

        return detailProjectVO;
    }
}
