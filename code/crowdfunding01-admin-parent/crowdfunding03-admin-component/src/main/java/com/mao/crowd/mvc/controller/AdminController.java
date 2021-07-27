package com.mao.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.mao.crowd.constant.CrowdConstant;
import com.mao.crowd.entity.Admin;
import com.mao.crowd.service.api.AdminService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/11 20:55
 * Description: 管理员控制器
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录验证
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {
        // 调用 Service 方法执行登录检查
        // 这个方法如果能够返回 admin 对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);

        // 登录成功后返回的 admin 对象存入 Session 域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/page.html";
    }


    /**
     * 注销功能
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {

        // 强制 session 失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 去管理员首页，但我发现如果用 view-controller 的话异常处理的
     * @ControllerAdvice 扫描不到这个请求，拦截器也就没办法做到真正的处理
     * 所以吧他这放到 Controller 这里
     * @return
     */
    @RequestMapping("/admin/to/main/page.html")
    public String goMain(){
        return "admin-main";
    }

    /**
     * 根绝关键词查询管理员，并使用 pageHelper 插件分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
            // 使用 @RequestParam 注解的 defaultValue 属性，指定默认值，在请求中没有指定默认参数时使用默认值
            // keyword 默认值使用空字符串，和 SQL 语句配合实现两种情况适配
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            Model model
    ) {

        // 调用 Service 方法获取 PageInfo 对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将 PageInfo 存入模型
        model.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";
    }


    /**
     * 删除管理员
     * @param adminId
     * @param pageNum
     * @param keyword
     * @return
     */
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
                         ) {

        // 执行删除
        adminService.remove(adminId);

        // 页面跳转：回到分页面

        // 尝试方案1：直接转发到 admin-page.jsp，会无法显示分页数据
        // return "admin-page";

        // 尝试方案2：转发到 /admin/get/page.html 地址，一旦刷新页面会重复执行删除浪费性能
        // return "forward:/admin/get/page.html";

        // 尝试方案3：重定向到 /admin/get/page.html 地址
        // 同时为了保持原本所在的页面和查询关键词再附加 pageNum、pageSize 两个参数
        return "redirect:/admin/get/page.html?keyword=" + keyword + "&pageNum=" + pageNum;
    }


    /**
     * 添加管理员
     * @param admin 管理员对象
     * @return
     */
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {

        adminService.saveAdmin(admin);

        // 重定向会原本的页面，且为了能在添加管理员后看到管理员，设置pageNum为整型的最大值（通过修正到最后一页）
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }


    /**
     * 去修改管理员的页面，其中需要将 admin 根据 adminId 查询出来回显数据到修改页面
     * @param adminId   管理员 id
     * @param model 传输数据模型
     * @return
     */
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            Model model
    ) {

        // 1、根据 adminId 查询到 Admin 对象
        Admin admin = adminService.getAdminById(adminId);

        // 2、将 Admin 对象存入 model
        model.addAttribute("admin",admin);

        return "admin-update";
    }


    /**
     * 更新管理员
     * @param admin 更新管理员信息
     * @param pageNum   当前管理员信息页码
     * @param keyword   查询关键字
     * @return
     */
    @RequestMapping("/admin/update.html")
    public String update(
            Admin admin,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword
    ) {

        adminService.updateAdmin(admin);

        return "redirect:/admin/get/page.html?keyword=" + keyword + "&pageNum=" + pageNum;
    }




    /**
     * 可以验证方法的返回值是否与设定的值相等
     * 比如方法的返回值需要与登录的用户名相等
     * 只有登录的用户名是 adminOperator 才有权限访问这个方法
     * @return
     */
    @PostAuthorize("returnObject.data.loginAcct == principal.username")
    @ResponseBody
    @GetMapping("/admin/test/post/filter.json")
    public ResultEntity<Admin> getAdminById() {

        Admin admin = new Admin();

        admin.setLoginAcct("adminOperator");

        return ResultEntity.successWithData(admin);
    }

    /**
     * 只能对集合类型的数据进行过滤
     * 对用户传递进方法的参数进行过滤
     * 例如对传递进来的数字集合进行过滤，
     * 过滤掉不是偶数的数字，方法只能接收到偶数
     *
     * 还有一个 @PostFilter 注解：在方法执行后对方发返回值进行过滤，只能对集合类型的数据进行过滤
     * @param valueList
     * @return
     */
    @PreFilter(value = "filterObject%2==0")
    @ResponseBody
    @RequestMapping("/admin/test/pre/filter.json")
    public ResultEntity<List<Integer>> saveList(@RequestBody List<Integer> valueList) {
        return ResultEntity.successWithData(valueList);
    }
}
