package com.cj.atcrowdfunding.mvc.config;

import com.cj.atcrowdfunding.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 临时使用内存版登录的模式测试代码
        //builder.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html")// 针对登录页进行设置
                .permitAll() // 无条件访问
                .antMatchers("/index.jsp")
                .permitAll()
                .antMatchers("/admin/get/page.do")
                .hasRole("经理")
                /*.antMatchers("/role/get/page/info.json")
                .hasRole("部长")*/
                .antMatchers("/bootstrap/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/crowd/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/css/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/fonts/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/img/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/jquery/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/layer/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/script/**") // 针对静态资源进行设置，无条件访问
                .permitAll() // 针对静态资源进行设置，无条件访问
                .antMatchers("/ztree/**") // 针对静态资源进行设置，无条件访问
                .permitAll()
                .antMatchers("/admin/to/main/page.html")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .csrf() // 防跨站请求伪造功能
                .disable() // 禁用
                .formLogin() // 开启表单登录的功能
                .loginPage("/admin/to/login/page.html") // 指定登录页面
                .loginProcessingUrl("/security/do/login.html") // 指定处理登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html")// 指定登录成功后前往的地址
                .usernameParameter("loginAcct") // 账号的请求参数名称
                .passwordParameter("userPswd") // 密码的请求参数名称
                .and()
                .logout()
                .logoutUrl("/admin/do/logout.do")
                .logoutSuccessUrl("/admin/to/login/page.html")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", new
                                Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                    }
                })

                ;

    }


}
