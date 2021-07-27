package com.mao.crowd.mvc.controller;

import com.mao.crowd.entity.Menu;
import com.mao.crowd.service.api.MenuService;
import com.mao.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Administrator
 * Date: 2021/7/13 18:20
 * Description: Menu 控制器
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * Get 请求获取全部的菜单，并且将其转换成树结构
     * @return
     */
    @GetMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree() {

        // 1、查询全部的 Menu 对象
        List<Menu> menuList = menuService.getAll();

        // 2、声明一个变量用来存储找到的根节点
        Menu root = null;

        // 3、创建一个 map 集合存储 menu 的 id
        Map<Integer,Menu> menuMap = new HashMap<>();

        // 4、遍历 menuList，先将 menu 的 id 存入 map 集合，以便寻找父节点
        for (Menu menu : menuList) {

            // 5、获取当前 menu 对象的 id 值
            Integer id = menu.getId();

            // 6、将 id 存入 map 集合
            menuMap.put(id,menu);
        }
        // 7、再次遍历 menuList 集合，寻找出根节点 root，以及各节点的父节点
        for (Menu menu : menuList) {

            // 8、获取父节点 pid
            Integer pid = menu.getPid();
            // 9、如果 pid 为 null，说明是根节点
            if(pid == null) {

                // 10、把当前正在遍历的这个 menu 对象赋值给 root
                root = menu;

                // 11、找到根节点，停止本次循环，继续执行下一次循环
                continue;
            }
            // 12、如果 pid 不为 null，说明当前节点有父节点，找到父节点就可以进行组装，建立父子关系
            Menu father = menuMap.get(pid);
            // 存入父节点的 children 集合
            father.getChildren().add(menu);
        }
        // 13、将组装好的树形结构（也就是根节点对象）返回浏览器对象，返回根节点就是返回了整棵树
        return ResultEntity.successWithData(root);
    }

    /**
     * 添加菜单 menu
     * @param menu
     * @return
     */
    @PostMapping("/menu/save.json")
    public ResultEntity<String> saveMenu(@RequestBody Menu menu) {

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }


    /**
     * Put 请求更新 menu
     * @param menu
     * @return
     */
    @PutMapping("/menu/update.json")
    public ResultEntity<String> updateMenu(@RequestBody Menu menu) {

        menuService.updateMenu(menu);

        return ResultEntity.successWithoutData();
    }


    /**
     * delete 请求删除 menu
     * @param id
     * @return
     */
    @DeleteMapping("/menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestBody Integer id) {

        menuService.removeMenu(id);

        return ResultEntity.successWithoutData();
    }
}
