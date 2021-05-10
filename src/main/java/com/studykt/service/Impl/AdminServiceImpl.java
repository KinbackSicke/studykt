package com.studykt.service.Impl;

import com.studykt.entity.Admin;
import com.studykt.entity.User;
import com.studykt.entity.UserExample;
import com.studykt.mapper.AdminMapper;
import com.studykt.mapper.UserMapper;
import com.studykt.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Admin selectById(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return adminMapper.selectByPrimaryKey(username);
    }

    @Override
    public List<User> selectAllUsers() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andOpenidLike("%%");
        return userMapper.selectByExample(userExample);
    }
}
