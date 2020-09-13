package com.cj.atcrowdfunding.mvc.config;

import com.cj.atcrowdfunding.entity.Admin;
import com.cj.atcrowdfunding.entity.Role;
import com.cj.atcrowdfunding.service.api.AdminService;
import com.cj.atcrowdfunding.service.api.AuthService;
import com.cj.atcrowdfunding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin=adminService.getAdminByLoginAcct(username);

        Integer adminId=admin.getId();

        List<Role> roleList= roleService.getAssignRole(adminId);

        List<String> authList=authService.getAssignedAuthNameByAdminId(adminId);

        List<GrantedAuthority> authorities=new ArrayList<>();

        for (Role role : roleList) {
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new
                    SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        for (String authName : authList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new
                    SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);

        return securityAdmin;
    }
}
