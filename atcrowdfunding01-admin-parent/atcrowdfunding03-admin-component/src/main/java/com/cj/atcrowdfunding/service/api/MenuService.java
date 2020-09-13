package com.cj.atcrowdfunding.service.api;

import com.cj.atcrowdfunding.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    void removeMenu(Integer id);

    void updateMenu(Menu menu);

    void saveMenu(Menu menu);
}
