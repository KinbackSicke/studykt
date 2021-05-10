package com.studykt.service;

import com.studykt.controller.vo.CourseCommentVO;
import com.studykt.controller.vo.CourseCommentVO2;
import com.studykt.entity.CourseComment;

import java.util.List;

public interface CourseCommentService {

    List<CourseCommentVO> selectByCourseId(String courseId);

    List<CourseCommentVO2> selectByUserId(String userId);

    int addComment(CourseComment courseComment);

    int deleteCommentById(String commentId);

    Long getCommentCountByCourseId(String courseId);

    int deleteCommentByCourseId(String courseId);
}
