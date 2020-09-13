package com.cj.atcrowdfunding.mvc.handler;

import com.cj.atcrowdfunding.constant.CrowdConstant;
import com.cj.atcrowdfunding.entity.Admin;
import com.cj.atcrowdfunding.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "admin/edit.do")
    public String editDb(Admin admin,@RequestParam("pageNum")Integer pageNum,@RequestParam("keyword") String keyword){

        adminService.update(admin);

        return "redirect:/admin/get/page.do?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping(value = "/admin/to/edit.html")
    public String edit(
            @RequestParam("adminId") Integer id,
            ModelMap modelMap
    ){
        Admin admin=adminService.queryById(id);
        modelMap.addAttribute("admin",admin);
        return "admin-edit";
    }

    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping(value = "/admin/add.do")
    public String save(Admin admin){

        adminService.saveUser(admin);

        return "redirect:/admin/get/page.do?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping(value = "admin/remove/{adminId}/{pageNum}/{keyword}.do")
    public String removeById(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ){

        adminService.remove(adminId);
        return "redirect:/admin/get/page.do?pageNum="+pageNum+"&keyword="+keyword;
    }


    @RequestMapping(value = "/admin/get/page.do")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue ="") String keyword,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        PageInfo<Admin> pageInfo=adminService.getPageInfo(keyword,pageNum,pageSize);

        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";
    }



    @RequestMapping(value = "admin/do/logout.do")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping(value = "admin/login.do")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ){
        Admin admin=adminService.getAdminByLoginAcct(loginAcct,userPswd);
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
}
