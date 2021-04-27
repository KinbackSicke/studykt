package com.studykt.controller;


import com.studykt.controller.vo.CourseCommentVO;
import com.studykt.controller.vo.CourseCommentVO2;
import com.studykt.entity.CourseComment;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.CourseCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "api for course comment", tags = {"课程评论相关接口"})
@RestController
@RequestMapping("/courseComment")
public class CourseCommentController extends BaseController {

    @Autowired
    private CourseCommentService courseCommentService;

    @ApiOperation(value = "get course comments", notes = "获取课程评价api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getCourseComments")
    public CommonReturnType getCourseComments(String courseId) throws BusinessException {
        if (StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        List<CourseCommentVO> list = courseCommentService.selectByCourseId(courseId);
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "get user comments", notes = "获取用户评价api")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getUserComments")
    public CommonReturnType getUserComments(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        List<CourseCommentVO2> list = courseCommentService.selectByUserId(userId);
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "add a comment", notes = "添加评价api")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/addComment")
    public CommonReturnType addComment(@RequestBody CourseComment courseComment) throws BusinessException {
        if (courseComment == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isBlank(courseComment.getContent())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "请输入内容");
        }
        courseComment.setId(sid.nextShort());
        courseComment.setCreateTime(new Date());
        courseComment.setLikeCount(0);
        courseComment.setStatus(1);
        courseCommentService.addComment(courseComment);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "delete comment", notes = "删除用户评价api")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/deleteComments")
    public CommonReturnType deleteComments(String id) throws BusinessException {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        courseCommentService.deleteCommentById(id);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "count comment", notes = "统计用户评价数量api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/countComments")
    public CommonReturnType countComments(String courseId) throws BusinessException {
        if (StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        Long count = courseCommentService.getCommentCountByCourseId(courseId);
        return CommonReturnType.create(count);
    }


}
