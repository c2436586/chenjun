package com.cj.atcrowdfunding;

import com.cj.atcrowdfunding.entity.Admin;
import com.cj.atcrowdfunding.entity.Role;
import com.cj.atcrowdfunding.mapper.AdminMapper;
import com.cj.atcrowdfunding.mapper.RoleMapper;
import com.cj.atcrowdfunding.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    private Logger logger=LoggerFactory.getLogger(AppTest.class);

    @Test
    public void Test01()throws Exception{
        Connection connection=dataSource.getConnection();
        System.out.println(connection);
    }
    @Test
    public void Test02(){
        Admin admin=new Admin(null,"tom","123123","汤姆","tom@qq.com",null);
        int a= adminMapper.insert(admin);
        System.out.println(a);
    }

    @Test
    public void Test03(){

        Admin admin=adminMapper.selectByPrimaryKey(1);
        System.out.println(admin);
    }

    @Test
    public void Test04(){

        Logger logger= LoggerFactory.getLogger(AppTest.class);

        logger.debug("debug level!!!!");

        logger.info("info level!!!!");

        logger.warn("warn level!!!!");

        logger.error("error level!!!!");

    }



    @Test
    public void Test05(){
        Admin admin= adminService.queryById(1);
        System.out.println(admin);
    }


    @Test
    public void Test06(){
        Admin admin=new Admin(null,"jerry","000123","杰瑞","jerry@qq.com",null);
        adminService.saveUser(admin);
    }


    @Test
    public void Test07(){
        for (int i=0;i<=200;i++){
            Admin admin=new Admin(null,"loginAcct"+i,"password"+i,"name"+i,"email"+i,null);
            adminService.saveUser(admin);
        }

    }

    @Test
    public void Test08(){
        for (int i=1;i<=237;i++){
           roleMapper.insert(new Role(null,"name"+i));
        }

    }
}
