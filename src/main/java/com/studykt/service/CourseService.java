package com.studykt.service;

import com.studykt.controller.vo.CourseVO;
import com.studykt.entity.Course;
import com.studykt.utils.PageResult;

import java.util.List;

public interface CourseService {

    List<Course> selectCourseByTitle(String title);

    int addCourse(Course course);

    List<CourseVO> selectCoursesOrderByCreateTime(Integer numOfRecords);

    List<CourseVO> selectRecommendCourses(String courseId, Integer numOfRecords);

    Course selectCourseById(String courseId);

    int updateCourse(Course course);

    int updateCourseById(Course course);

    Integer selectCourseFavorCount(String courseId);

    PageResult getAllCourses(Integer curPage, Integer pageSize);

    PageResult getCoursesBySearchKey(String searchKey, Integer curPage, Integer pageSize);

    PageResult getCoursesByTagName(String searchKey, Integer curPage, Integer pageSize);

    List<CourseVO> selectHottestCourses(Integer numOfRecords);
}
