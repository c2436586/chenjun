package com.cj.atcrowdfunding.service.api;

import com.cj.atcrowdfunding.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService{
    Admin queryById(Integer id);

    void saveUser(Admin admin);

    Admin getAdminByLoginAcct(String loginAcct, String userPwd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> integerList);


    Admin getAdminByLoginAcct(String username);
}
