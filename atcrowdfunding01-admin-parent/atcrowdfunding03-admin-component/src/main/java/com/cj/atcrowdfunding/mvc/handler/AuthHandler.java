package com.cj.atcrowdfunding.mvc.handler;

import com.cj.atcrowdfunding.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthHandler {

    @Autowired
    private AuthService authService;


}
