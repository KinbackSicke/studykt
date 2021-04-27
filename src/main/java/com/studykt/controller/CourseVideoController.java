package com.studykt.controller;

import com.studykt.entity.Course;
import com.studykt.entity.CourseVideo;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.CourseService;
import com.studykt.service.CourseVideoService;
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
import java.util.List;

@Api(value = "course video api", tags = {"课程视频相关接口"})
@RestController
@RequestMapping("/courseVideo")
public class CourseVideoController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseVideoService courseVideoService;

    @ApiOperation(value = "add a course video", notes = "添加课程视频信息api")
    @ApiImplicitParam(name = "title", value="课程的标题", required=true, dataType="string", paramType="query")
    @PostMapping("/addCourseVideoByTitle")
    public CommonReturnType addCourseVideoByTitle(String title, @RequestBody CourseVideo courseVideo) throws BusinessException {
        if (courseVideo == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        courseVideo.setCreateTime(new Date());
        List<Course> list = courseService.selectCourseByTitle(title);
        if (list != null) {
            courseVideo.setCourseId(list.get(0).getId());
        } else {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "未找到课程");
        }
        courseVideoService.addCourseVideo(courseVideo);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "get all course videos", notes = "获取课程视频信息api")
    @ApiImplicitParam(name = "courseId", value="课程的编号", required=true, dataType="string", paramType="query")
    @PostMapping("/getCourseVideos")
    public CommonReturnType getCourseVideos(String courseId) throws BusinessException {
        if (StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        List<CourseVideo> list = courseVideoService.getVideosByCourseId(courseId);
        return CommonReturnType.create(list);
    }


}
