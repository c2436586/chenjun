package com.cj.atcrowdfunding.mvc.handler;

import com.cj.atcrowdfunding.entity.Role;
import com.cj.atcrowdfunding.service.api.RoleService;
import com.cj.atcrowdfunding.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RoleHandler {

    @Autowired
    private RoleService roleService;


    //@ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList){
        System.out.println("-------------");
        System.out.println(roleIdList.toString());
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }

    //@ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(Role role) {
        System.out.println(role.toString());
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    //@ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> savaRole(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @PreAuthorize("hasRole('部长')")
    //@ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ){
        // 调用service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }
}
