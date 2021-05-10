package com.studykt.service.Impl;

import com.studykt.entity.Feedback;
import com.studykt.entity.StudyRecord;
import com.studykt.entity.StudyRecordExample;
import com.studykt.entity.User;
import com.studykt.mapper.FeedbackMapper;
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

    @Autowired
    private FeedbackMapper feedbackMapper;

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
    public int deleteUser(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int addStudyRecord(StudyRecord studyRecord) {
        return studyRecordMapper.insertSelective(studyRecord);
    }

    @Override
    public Double selectTotalStudyTimeById(String userId) {
        if (StringUtils.isBlank(userId)) {
            return 0.;
        }
        Double res = studyRecordMapper.selectStudyDurationSumByUserId(userId);
        return (res == null ? 0. : res);
    }

    @Override
    public Double selectDailyStudyTimeById(String userId) {
        if (StringUtils.isBlank(userId)) {
            return 0.;
        }
        Double res = studyRecordMapper.selectStudyDurationSumByDay(userId);
        return (res == null ? 0. : res);
    }

    @Override
    public int addFeedback(Feedback feedback) {
        if (feedback == null) {
            return -1;
        }
        return feedbackMapper.insertSelective(feedback);
    }
}
