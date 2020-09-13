package com.cj.atcrowdfunding.service.impl;

import com.cj.atcrowdfunding.entity.Menu;
import com.cj.atcrowdfunding.mapper.MenuMapper;
import com.cj.atcrowdfunding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        List<Menu> menus= menuMapper.selectAll();
        return menus;
    }

    @Override
    public void removeMenu(Integer id) {

    }

    @Override
    public void updateMenu(Menu menu) {

    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }
}
