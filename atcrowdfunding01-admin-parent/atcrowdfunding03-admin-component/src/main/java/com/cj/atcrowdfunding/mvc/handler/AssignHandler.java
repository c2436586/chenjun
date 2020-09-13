package com.cj.atcrowdfunding.mvc.handler;

import com.cj.atcrowdfunding.entity.Auth;
import com.cj.atcrowdfunding.entity.Role;
import com.cj.atcrowdfunding.service.api.AdminService;
import com.cj.atcrowdfunding.service.api.AuthService;
import com.cj.atcrowdfunding.service.api.RoleService;
import com.cj.atcrowdfunding.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId
    ){
        List<Integer> roleList=authService.getAssignedAuthIdByRoleId(roleId);

        return ResultEntity.successWithData(roleList);
    }

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(
            @RequestBody Map<String,List<Integer>> map
            ){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }


    @ResponseBody
    @RequestMapping(value = "/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList=authService.getAll();
        return ResultEntity.successWithData(authList);
    }


    @RequestMapping(value = "assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer id,
            ModelMap modelMap

    ){
        List<Role> assignList=roleService.getAssignRole(id);

        List<Role> unAssignList=roleService.getUnAssignRole(id);

        modelMap.addAttribute("assignList",assignList);

        modelMap.addAttribute("unAssignList",unAssignList);

        return "assign-role";
    }

    @RequestMapping(value = "assign/do/role/assign.html")
    public String SaveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam(value ="keyword",defaultValue ="") String keyword,
            @RequestParam(value="roleIdList" ,required = false)List<Integer> roleIdList
    ){
        adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/get/page.do?pageNum="+pageNum+"&keyword="+keyword;
    }
}
