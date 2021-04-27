package com.studykt.mapper;

import com.studykt.controller.vo.CourseCommentVO;
import com.studykt.controller.vo.CourseCommentVO2;

import java.util.List;

public interface CourseCommentMapperCustom {

    List<CourseCommentVO> selectByCourseId(String courseId);

    List<CourseCommentVO2> selectByUserId(String userId);

}
