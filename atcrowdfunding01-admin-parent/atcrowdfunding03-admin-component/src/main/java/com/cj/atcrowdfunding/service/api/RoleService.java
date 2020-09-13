package com.cj.atcrowdfunding.service.api;

import com.cj.atcrowdfunding.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService{


    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    List<Role> getAssignRole(Integer id);

    List<Role> getUnAssignRole(Integer id);

    void removeRole(List<Integer> roleIdList);

    void updateRole(Role role);

    void saveRole(Role role);
}
