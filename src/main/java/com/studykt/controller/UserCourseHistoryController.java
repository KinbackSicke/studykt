package com.studykt.controller;


import com.alibaba.fastjson.JSONObject;
import com.studykt.controller.vo.CourseHistoryVO;
import com.studykt.entity.Course;
import com.studykt.entity.UserCourseHistory;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.CourseService;
import com.studykt.service.UserCourseHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "Api for user course history operations", tags = {"用户课程历史相关操作"})
@RequestMapping("/userCourseHistory")
public class UserCourseHistoryController extends BaseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "add user course history", notes = "添加用户课程历史信息到redis中api")
    @PostMapping("/addUserCourseHistory")
    public CommonReturnType addUserCourseHistory(@RequestBody UserCourseHistory userCourseHistory) throws BusinessException {
        if (userCourseHistory == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        } else if (StringUtils.isBlank(userCourseHistory.getCourseId()) || StringUtils.isBlank(userCourseHistory.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String userId = userCourseHistory.getUserId();
        String courseId = userCourseHistory.getCourseId();
        String key = USER_COURSE_HISTORY + ":" + userId;
        redis.removeValue(key, courseId);
        redis.leftPush(key, courseId);
        // 设置过期时间一个月
        redis.expire(key, BASE_REDIS_TIMEOUT * 24 * 30);
        return CommonReturnType.create();
    }


    @ApiOperation(value = "get course history by userid", notes = "根据用户id获取用户历史看过的课程api")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getUserCourseHistory")
    public CommonReturnType getUserCourseHistory(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        String key = USER_COURSE_HISTORY + ":" + userId;
        List<String> list = redis.lrange(key, 0, -1);
        List<CourseHistoryVO> courseList = new ArrayList<>();
        for (String id : list) {
            Course course = courseService.selectCourseById(id);
            if (course != null) {
                CourseHistoryVO courseHistoryVO = new CourseHistoryVO();
                BeanUtils.copyProperties(course, courseHistoryVO);
                courseHistoryVO.setFavorCount(courseService.selectCourseFavorCount(course.getId()));
                courseList.add(courseHistoryVO);
            }
        }
        return CommonReturnType.create(courseList);
    }


}
