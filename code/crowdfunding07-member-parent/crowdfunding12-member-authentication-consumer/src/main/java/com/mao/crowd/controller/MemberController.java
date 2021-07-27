package com.mao.crowd.controller;

import com.mao.crowd.api.MySQLRemoteService;
import com.mao.crowd.api.RedisRemoteService;
import com.mao.crowd.config.ShortMessageProperties;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.po.MemberPO;
import com.mao.crowd.entity.vo.MemberLoginVO;
import com.mao.crowd.entity.vo.MemberVO;
import com.mao.crowd.util.CrowdUtil;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Author: Administrator
 * Date: 2021/7/21 11:18
 * Description:
 */
@Controller
public class MemberController {

    // 获取发送短信所需要的属性
    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    /**
     * 将验证码发送至 phoneNum 手机
     * @param phoneNum
     * @return
     */
    @ResponseBody
    @PostMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {

        // 1、先将验证码存入 redis，以免 redis 宕机导致收到短信但提示不可用
        // ① 生成验证码
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * 10);
            stringBuilder.append(random);
        }
        String code = stringBuilder.toString();

        // 2、将验证码存入 redis
        // ① 拼接一个用于在 redis 中存储数据的 key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeOut(key, code, 15, TimeUnit.MINUTES);

        // 判断结果
        if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
            // 如果成功将验证码保存到 redis，调用远程服务发送短信验证码到 phoneNum 手机
            ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(
                    shortMessageProperties.getHost(),
                    shortMessageProperties.getPath(),
                    shortMessageProperties.getMethod(),
                    phoneNum,
                    code,
                    shortMessageProperties.getAppCode(),
                    shortMessageProperties.getSmsSignId(),
                    shortMessageProperties.getTemplateId()
            );

            // 3、判断短信的发送结果
            if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            }else {
                return sendMessageResultEntity;
            }
        }else {
            return saveCodeResultEntity;
        }
    }


    /**
     * 进行用注册的操作
     * 涉及到验证码验证，用户名查重，redis 验证码删除，用户保存操作
     * @param memberVO
     * @param model
     * @return
     */
    @PostMapping("/auth/do/member/register")
    public String register(
            MemberVO memberVO,
            Model model) {

        // 1、获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();

        // 2、拼 redis 中存储验证码的 key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        // 3、从 redis 读取 key 的value
        ResultEntity<String> redisCodeResultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);

        // 4、检查查询操作是否有效
        String result = redisCodeResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            // 如果获取验证码失败
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, redisCodeResultEntity.getMessage());

            return "member-reg";
        }

        String redisCode = redisCodeResultEntity.getData();

        if (redisCode == null) {
            // 验证码已过期
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        // 5、如果从 redis 能够查询到 value，则比较表单验证码和 redis 验证码
        String formCode = memberVO.getCode();

        if (!Objects.equals(redisCode,formCode)) {
            // 验证码不相等
           model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
           return "member-reg";
        }

        // 6、如果验证码一致，则将 redis 中的验证码删除，避免用户重复使用
        redisRemoteService.removeRedisKeyRemote(key);

        // 7、执行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswdBeforeEncode = memberVO.getUserpswd();

        String userpswdAfterEncode = passwordEncoder.encode(userpswdBeforeEncode);

        memberVO.setUserpswd(userpswdAfterEncode);

        // 8、调用远程 mysql 服务，执行添加保存用户
        // ① 创建 MemberPO 对象
        MemberPO memberPO = new MemberPO();

        // ② 复制属性
        BeanUtils.copyProperties(memberVO,memberPO);

        // ③ 调用远程服务保存用户
        ResultEntity<String> saveMemberPOResultEntity = mySQLRemoteService.saveMemberPO(memberPO);

        // 9、保存失败，可能是账号已经存在，需要重新设置
        if (ResultEntity.FAILED.equals(saveMemberPOResultEntity.getResult())) {

            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberPOResultEntity.getMessage());

            return "member-reg";
        }

        // 使用重定向避免刷新浏览器导致重新执行注册流程
        return "redirect://auth/member/to/login/page";
    }


    /**
     * 用户登录，包括根据用户名查询用户，密码验证比较
     * 以及将 loginMember 对象存入 session 域
     * @param loginacct
     * @param userpswd
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/auth/member/do/login")
    public String doLogin(
            @RequestParam("loginacct") String loginacct,
            @RequestParam("userpswd") String userpswd,
            Model model,
            HttpSession session
    ) {

        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);

        // 查询用户出错
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {

            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());

            return "member-login";
        }

        MemberPO memberPO = resultEntity.getData();

        // 用户名错误
        if(memberPO == null) {
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 2、从 memberPO 中取得密码与表单密码进行比较
        String userpswdDataBase = memberPO.getUserpswd();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 将表单密码进行加密比较
        boolean result = passwordEncoder.matches(userpswd, userpswdDataBase);

        if (!result) {
            // 如果密码错误
            model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 3、密码正确，创建 memberLoginPO 对象存入 Session 域
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(),memberPO.getUsername(),memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);

        return "redirect:/auth/member/to/center/page";
    }


    /**
     * 注销，退出登录
     * 跳转到首页
     * @param session
     * @return
     */
    @GetMapping("/auth/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/test/session")
    public String testSession(HttpSession session) {
        MemberLoginVO loginMember = (MemberLoginVO) session.getAttribute("loginMember");
        System.out.println(loginMember);
        return "ok";
    }
}
