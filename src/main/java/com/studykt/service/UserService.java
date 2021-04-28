package com.studykt.service;

import com.studykt.entity.StudyRecord;
import com.studykt.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    User validateLogin(User user);

    User selectByOpenid(String openid);

    int addUser(User user);

    int updateUser(User user);

    int addStudyRecord(StudyRecord studyRecord);

}
