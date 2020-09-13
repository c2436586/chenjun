package com.cj.atcrowdfunding.service.impl;

import com.cj.atcrowdfunding.constant.CrowdConstant;
import com.cj.atcrowdfunding.entity.Admin;
import com.cj.atcrowdfunding.entity.AdminExample;
import com.cj.atcrowdfunding.exception.LoginAcctAlreadyUseException;
import com.cj.atcrowdfunding.exception.LoginAcctAlreadyUseForUpdateException;
import com.cj.atcrowdfunding.exception.LoginFailedException;
import com.cj.atcrowdfunding.mapper.AdminMapper;
import com.cj.atcrowdfunding.service.api.AdminService;
import com.cj.atcrowdfunding.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample  example=new AdminExample();
        AdminExample.Criteria criteria=example.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins=adminMapper.selectByExample(example);
        Admin admin=admins.get(0);
        return admin;
    }

    @Override
    public void update(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        adminMapper.deleteOLdRelationship(adminId);
        if (roleIdList!=null&& roleIdList.size()>0){
            adminMapper.insertNewRelationship(adminId,roleIdList);
        }

    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin queryById(Integer id) {
        Admin admin=adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Admin> list=adminMapper.selectAdminByKeyword(keyword);

        return new PageInfo<>(list);
    }

    @Override
    public void saveUser(Admin admin) {


        String userPswd=admin.getUserPswd();
        //userpwd=CrowdUtil.md5(userpwd);
        userPswd=passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);

        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mmm:ss");
        String createTime=format.format(date);
        admin.setCreateTime(createTime);

        try{
            adminMapper.insert(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {
        AdminExample adminExample=new AdminExample();
        AdminExample.Criteria criteria=adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> List=adminMapper.selectByExample(adminExample);
        if (List==null || List.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (List.size()>1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin=List.get(0);
        String userPwdDB=admin.getUserPswd();
        String userPwdForm= CrowdUtil.md5(userPwd);
        if (!Objects.equals(userPwdDB,userPwdForm)){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }
}
