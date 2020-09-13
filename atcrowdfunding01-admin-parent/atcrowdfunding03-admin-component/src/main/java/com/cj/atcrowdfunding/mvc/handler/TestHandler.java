package com.cj.atcrowdfunding.mvc.handler;

import com.cj.atcrowdfunding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/test/ssm.do")
    public String Test(){
        System.out.println(10/0);
        return "result";
    }
}
