package com.studykt.service.Impl;


import com.studykt.controller.vo.CourseCommentVO;
import com.studykt.controller.vo.CourseCommentVO2;
import com.studykt.entity.CourseComment;
import com.studykt.entity.CourseCommentExample;
import com.studykt.mapper.CourseCommentMapper;
import com.studykt.mapper.CourseCommentMapperCustom;
import com.studykt.service.CourseCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {

    @Autowired
    private CourseCommentMapper courseCommentMapper;


    @Autowired
    private CourseCommentMapperCustom courseCommentMapperCustom;


    @Override
    public List<CourseCommentVO> selectByCourseId(String courseId) {
        if (courseId == null) {
            return null;
        }
        return courseCommentMapperCustom.selectByCourseId(courseId);
    }

    @Override
    public List<CourseCommentVO2> selectByUserId(String userId) {
        if (userId == null) {
            return null;
        }
        return courseCommentMapperCustom.selectByUserId(userId);
    }

    @Override
    public int addComment(CourseComment courseComment) {
        return courseCommentMapper.insertSelective(courseComment);
    }

    @Override
    public int deleteCommentById(String commentId) {
        return courseCommentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public Long getCommentCountByCourseId(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            return 0L;
        }
        CourseCommentExample example = new CourseCommentExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        return courseCommentMapper.countByExample(example);
    }

    @Override
    public int deleteCommentByCourseId(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            return -1;
        }
        CourseCommentExample example = new CourseCommentExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        return courseCommentMapper.deleteByExample(example);
    }
}
