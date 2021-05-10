package com.studykt.mapper;

import com.studykt.controller.vo.CourseFavorVO;
import com.studykt.controller.vo.CourseVO;
import com.studykt.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapperCustom {

    List<CourseVO> selectByCreateDateDESC(Integer numOfRecords);

    List<CourseVO> selectByStudyCountDESC(Integer numOfRecords);

    List<CourseVO> selectAllCourses();

    CourseVO selectCourseById(String courseId);

    Integer selectFavorCount(String courseId);

    List<CourseFavorVO> selectFavorCoursesByUserId(String userId);

    List<CourseVO> selectByCourseTitle(String searchKey);

    List<CourseVO> selectByTagName(String searchKey);

    List<CourseVO> selectByTitleAndTagName(String searchKey);

    List<CourseVO> selectRecommendedById(@Param("courseId") String courseId, @Param("numOfRecords") Integer numOfRecords);
}
