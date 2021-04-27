package com.studykt.service;

import com.studykt.controller.vo.CourseFavorVO;
import com.studykt.entity.UserFavorCourse;

import java.util.List;

public interface UserFavorCourseService {

    int addFavorCourse(UserFavorCourse userFavorCourse);

    int removeFavorCourse(String userId, String courseId);

    int removeFavorCourseById(String id);

    List<UserFavorCourse> selectFavorCourses(String userId, String courseId);

    List<CourseFavorVO> selectFavorCourseByUserId(String userId);
}
