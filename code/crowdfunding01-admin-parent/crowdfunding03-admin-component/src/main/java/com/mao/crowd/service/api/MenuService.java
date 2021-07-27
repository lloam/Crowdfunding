package com.mao.crowd.service.api;

import com.mao.crowd.entity.Menu;

import java.util.List;

/**
 * Author: Administrator
 * Date: 2021/7/13 18:19
 * Description:
 */
public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
