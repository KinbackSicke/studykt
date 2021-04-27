package com.studykt.controller;


import com.studykt.controller.vo.CourseFavorVO;
import com.studykt.entity.Course;
import com.studykt.entity.UserFavorCourse;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.CourseService;
import com.studykt.service.UserFavorCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Api(value = "Api for user favor courses", tags = {"用户收藏课程API"})
@RequestMapping("/favorCourse")
public class UserFavorCourseController extends BaseController {

    @Autowired
    private UserFavorCourseService userFavorCourseService;

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "add a user favored course", notes = "添加用户收藏课程api")
    @PostMapping("/addFavorCourse")
    public CommonReturnType addFavorCourse(@RequestBody UserFavorCourse userFavorCourse) throws BusinessException {
        if (userFavorCourse == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        } else if (StringUtils.isBlank(userFavorCourse.getCourseId()) || StringUtils.isBlank(userFavorCourse.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "信息不存在");
        }
        userFavorCourse.setId(sid.nextShort());
        userFavorCourse.setFavorDate(new Date());
        if (userFavorCourseService.addFavorCourse(userFavorCourse) == 0) {
            return CommonReturnType.create(userFavorCourse.getId());
        } else {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "remove a user favored course", notes = "取消用户收藏课程api")
    @ApiImplicitParam(name = "id", value = "用户收藏课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/removeFavorCourse")
    public CommonReturnType removeFavorCourse(String id) throws BusinessException {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (userFavorCourseService.removeFavorCourseById(id) == 0) {
            return CommonReturnType.create();
        } else {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation(value = "Is user favor a course", notes = "用户是否收藏课程api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "courseId", value = "课程id", required = true,
                    dataType = "string", paramType = "query")
    })
    @PostMapping("/isFavored")
    public CommonReturnType isFavored(String userId, String courseId) throws BusinessException {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<UserFavorCourse> list = userFavorCourseService.selectFavorCourses(userId, courseId);
        if (list == null || list.size() == 0) {
            return CommonReturnType.create(null);
        }
        return CommonReturnType.create(list.get(0).getId());
    }

    @ApiOperation(value = "Get all favor courses", notes = "获取用户所有收藏课程")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/allFavored")
    public CommonReturnType allFavored(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        List<CourseFavorVO> list = userFavorCourseService.selectFavorCourseByUserId(userId);
        return CommonReturnType.create(list);
    }

}
