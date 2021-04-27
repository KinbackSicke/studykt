package com.studykt.service.Impl;

import com.studykt.controller.vo.CourseFavorVO;
import com.studykt.entity.UserFavorCourse;
import com.studykt.entity.UserFavorCourseExample;
import com.studykt.mapper.CourseMapperCustom;
import com.studykt.mapper.UserFavorCourseMapper;
import com.studykt.service.UserFavorCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavorCourseImpl implements UserFavorCourseService {

    @Autowired
    private UserFavorCourseMapper userFavorCourseMapper;

    @Autowired
    private CourseMapperCustom courseMapperCustom;

    @Override
    public int addFavorCourse(UserFavorCourse userFavorCourse) {
        userFavorCourseMapper.insertSelective(userFavorCourse);
        return 0;
    }

    @Override
    public int removeFavorCourse(String userId, String courseId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(courseId)) {
            return -1;
        }
        UserFavorCourseExample example = new UserFavorCourseExample();
        example.createCriteria().andCourseIdEqualTo(courseId).andUserIdEqualTo(userId);
        userFavorCourseMapper.deleteByExample(example);
        return 0;
    }

    @Override
    public int removeFavorCourseById(String id) {
        userFavorCourseMapper.deleteByPrimaryKey(id);
        return 0;
    }

    @Override
    public List<UserFavorCourse> selectFavorCourses(String userId, String courseId) {
        UserFavorCourseExample example = new UserFavorCourseExample();
        if (courseId == null) {
            example.createCriteria().andUserIdEqualTo(userId);
        } else {
            example.createCriteria().andUserIdEqualTo(userId).andCourseIdEqualTo(courseId);
        }
        return userFavorCourseMapper.selectByExample(example);
    }

    @Override
    public List<CourseFavorVO> selectFavorCourseByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return courseMapperCustom.selectFavorCoursesByUserId(userId);
    }

}
