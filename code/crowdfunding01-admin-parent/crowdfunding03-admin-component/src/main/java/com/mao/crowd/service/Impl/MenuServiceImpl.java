package com.mao.crowd.service.Impl;

import com.mao.crowd.entity.Menu;
import com.mao.crowd.entity.MenuExample;
import com.mao.crowd.mapper.MenuMapper;
import com.mao.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/13 18:20
 * Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取全部的 Menu
     * @return
     */
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    /**
     * 添加 menu
     * @param menu
     */
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    /**
     * 更新菜单 menu
     * @param menu
     */
    public void updateMenu(Menu menu) {

        // 由于 pid 没有传入，一定要使用有选择的更新，保证 pid 不会在更新中置为 null
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 根据 id 删除 Menu
     * @param id
     */
    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
