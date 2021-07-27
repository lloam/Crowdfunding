package com.mao.crowd.controller;

import com.mao.crowd.api.MySQLRemoteService;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.entity.vo.*;
import com.mao.crowd.util.ResultEntity;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/23 9:50
 * Description:
 */
@Controller
public class ProjectConsumerController {


    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 上传众筹项目
     * @param projectVO
     * @param headerPicture
     * @param detailPictureList
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/create/project/information")
    public String saveProjectBasicInfo(
            // 接收前端项目数据
            ProjectVO projectVO,

            // 接收上传的头图
            MultipartFile headerPicture,

            // 接收上传的详情图片
            List<MultipartFile> detailPictureList,

            // 用来将收集了一部分数据的 ProjectVO 对象存入 session 域
            HttpSession session,
            Model model
    ) {

        // 1、完成·头图的上传
        // ① 判断上传的头图是否成功
        boolean headerPictureIsEmpty = headerPicture.isEmpty();

        if (!headerPictureIsEmpty) {

            // 2、如果用户确实上传了有内容的图片则执行上传
            // ① 这里没有用 oss 对象存储，直接给他一个图片地址了
            String headerPicturePath = "https://picsum.photos/536/354";

            // 3、存入 ProjectVO 对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        }else {
            // 如果 headerPicture 为空，返回提交页面，并且提示消息
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);

            return "project-launch";
        }

        // 4、遍历 detailPictureList 集合
        List<String> detailPicturePathList = new ArrayList<>();

        // 5、检查 detailPicturePathList 集合是否有效
        if (detailPictureList == null || detailPictureList.size() == 0) {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);

            return "project-launch";
        }

        for (MultipartFile multipartFile : detailPictureList) {

            // 6、当前 detailPicture 是否为空
            boolean detailPicture = multipartFile.isEmpty();

            if(!detailPicture) {
                // 7、依旧是没有使用 oss 对象存储，直接给图片地址
                String detailPicturePath = "https://picsum.photos/536/355";

                detailPicturePathList.add(detailPicturePath);
            }
        }
        // 8、遍历完成将详情图片的访问路径集合存入 ProjectVO
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 9、、将 ProjectVO 存入 session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 10、去下一个页面
        return "redirect:/project/return/info/page";
    }


    /**
     * ajax 请求上传回报图片
     * @param returnPicture
     * @return
     */
    // JavaScript 代码：formData.append("returnPicture", file);
    // returnPicture 是请求参数的名字
    // file 是请求参数的值，也就是要上传到文件
    @ResponseBody
    @PostMapping("/create/upload/return/picture.json")
    public ResultEntity<String> createUploadReturnPic(
            // 接收用户上传的文件
            @RequestParam("returnPicture") MultipartFile returnPicture) {

        boolean uploadReturnPicIsEmpty = returnPicture.isEmpty();

        // 判断上传的图片是否为空
        if (uploadReturnPicIsEmpty) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_RETURN_PIC_FAILED);
        }

        // 如果不为空，本来应该用 oss 对象存储的，这里不用，只返回图片路径
        String uploadReturnPicPath = "https://picsum.photos/id/1014/367/267";

        return ResultEntity.successWithData(uploadReturnPicPath);
    }


    /**
     * 将 returnVO 保存到 projectVO
     * @param returnVO
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(
            @RequestBody ReturnVO returnVO,
            HttpSession session) {

        try {
            // 1、从 session 域中读取之前缓存的 ProjectVO 对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 2、判断 projectVO 是否为 null
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // 3、从 projectVO 对象中获取存储回报信息的集合
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 4、判断 returnVOList 是否有效
            if (returnVOList == null || returnVOList.size() == 0) {
                // 5、如果为空，就新建集合对象
                returnVOList = new ArrayList<>();
                // 6、将 集合设置回去
                projectVO.setReturnVOList(returnVOList);
            }

            // 7、将前端接收到的 returnVO 对下给你存入集合
            returnVOList.add(returnVO);

            // 8、把数据有变化的 projectVO 对象重新存入 session 域，与确保新的数据能够存入 redis 中
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

            // 9、所有操作成功返回
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            // 10、失败返回
            return ResultEntity.failed("服务器繁忙！请重试！");
        }
    }

    /**
     * 保存用户确认信息
     * @return
     */
    @PostMapping("/create/confirm")
    public String createConfirm(
            MemberConfirmInfoVO memberConfirmInfoVO,
            HttpSession session,
            Model model
    ) {

        // 1、将 projectVO 从 session 域中取出
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 2、如果 projectVO 为 null
        if (projectVO == null) {
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        // 3、将确认信息数据设置到 projectVO
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 4、从 session 域中获取此时登录的用户
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

        Integer memberId = memberLoginVO.getId();

        // 5、调用远程方法保存 projectVO 对象
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberId);

        // 6、判断远程保存操作是否成功
        String result = saveResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {

            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());

            return "project-confirm";
        }

        // 7、如果操作成功就将 session 域中保存的临时的 projectVO 删除
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 8、远程操作成功，跳转到最终成功的页面
        return "redirect:/project/create/success";
    }

    @GetMapping("/portal/show/project/detail/{projectId}")
    public String getProjectDetail(
            @PathVariable("projectId") Integer projectId,
            Model model) {
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {

            DetailProjectVO detailProjectVO = resultEntity.getData();

            model.addAttribute("detailProjectVO",detailProjectVO);
        }
        return "project-show-detail";
    }


    @ResponseBody
    @GetMapping("/test/mysql")
    public String test() {
        ResultEntity<MemberPO> harry = mySQLRemoteService.getMemberPOByLoginAcctRemote("harry");
        System.out.println(harry.getResult());
        System.out.println(harry.getMessage());
        System.out.println(harry.getData());
        return "车工";
    }
}
