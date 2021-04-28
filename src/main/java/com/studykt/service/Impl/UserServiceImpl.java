package com.studykt.service.Impl;

import com.studykt.entity.StudyRecord;
import com.studykt.entity.User;
import com.studykt.mapper.StudyRecordMapper;
import com.studykt.mapper.UserMapper;
import com.studykt.service.UserService;
import com.studykt.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudyRecordMapper studyRecordMapper;

    @Override
    public User validateLogin(User user) {
        
        return null;
    }

    @Override
    public User selectByOpenid(String openid) {
        if (StringUtils.isBlank(openid)) {
            return null;
        }
        return userMapper.selectByPrimaryKey(openid);
    }

    @Override
    public int addUser(User user) {
        userMapper.insertSelective(user);
        return 0;
    }

    @Override
    public int updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
        return 0;
    }

    @Override
    public int addStudyRecord(StudyRecord studyRecord) {
        return studyRecordMapper.insertSelective(studyRecord);
    }
}
