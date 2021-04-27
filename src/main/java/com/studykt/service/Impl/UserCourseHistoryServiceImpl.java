package com.studykt.service.Impl;

import com.studykt.entity.UserCourseHistory;
import com.studykt.mapper.UserCourseHistoryMapper;
import com.studykt.service.UserCourseHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCourseHistoryServiceImpl implements UserCourseHistoryService {

    @Autowired
    private UserCourseHistoryMapper userCourseHistoryMapper;

    @Override
    public int addUserCourseHistory(UserCourseHistory userCourseHistory) {
        if (userCourseHistory == null) {
            return -1;
        } else if (StringUtils.isBlank(userCourseHistory.getUserId()) || StringUtils.isBlank(userCourseHistory.getCourseId())) {
            return -1;
        }
        userCourseHistoryMapper.insertSelective(userCourseHistory);
        return 0;
    }
}
