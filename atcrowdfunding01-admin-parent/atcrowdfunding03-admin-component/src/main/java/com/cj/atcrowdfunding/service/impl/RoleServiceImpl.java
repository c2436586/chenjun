package com.cj.atcrowdfunding.service.impl;

import com.cj.atcrowdfunding.entity.Role;
import com.cj.atcrowdfunding.entity.RoleExample;
import com.cj.atcrowdfunding.mapper.RoleMapper;
import com.cj.atcrowdfunding.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum,pageSize);

        List<Role> list=roleMapper.selectRoleByKeyword(keyword);

        return new PageInfo<>(list);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample example=new RoleExample();
        RoleExample.Criteria criteria=example.createCriteria();

        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public List<Role> getAssignRole(Integer id) {
        return roleMapper.selectAssignRole(id);
    }

    @Override
    public List<Role> getUnAssignRole(Integer id) {
        return roleMapper.selectUnAssignRole(id);
    }
}
