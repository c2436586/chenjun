package com.cj.atcrowdfunding.service.api;

import com.cj.atcrowdfunding.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService{
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
