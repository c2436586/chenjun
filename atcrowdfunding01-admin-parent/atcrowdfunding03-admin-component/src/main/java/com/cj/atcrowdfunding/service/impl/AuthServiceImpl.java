package com.cj.atcrowdfunding.service.impl;

import com.cj.atcrowdfunding.entity.Auth;
import com.cj.atcrowdfunding.entity.AuthExample;
import com.cj.atcrowdfunding.mapper.AuthMapper;
import com.cj.atcrowdfunding.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{


    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthNameByAdminId(adminId);
    }

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        List<Integer> roleIdList=map.get("roleId");
        Integer roleId=roleIdList.get(0);
        authMapper.deleteOldRelation(roleId);

        List<Integer> authIdArray=map.get("authIdArray");

        if (authIdArray!=null&&authIdArray.size()>0)
        authMapper.insertNewRelationship(roleId,authIdArray);
    }
}
