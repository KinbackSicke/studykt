package com.studykt.controller;


import com.studykt.controller.vo.CourseVO;
import com.studykt.entity.Course;
import com.studykt.entity.SearchRecord;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.CourseService;
import com.studykt.service.SearchRecordService;
import com.studykt.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(value = "api for courses", tags = {"课程相关接口"})
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SearchRecordService searchRecordService;

    // 添加课程
    @ApiOperation(value = "add a course", notes = "添加课程信息api")
    @PostMapping("/addCourse")
    public CommonReturnType addCourse(@RequestBody Course course) throws BusinessException {
        if (course == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        course.setStudyCount(0);
        course.setCreateDate(new Date());
        courseService.addCourse(course);
        return CommonReturnType.create();
    }

    // 获取课程(已弃用)
    /*@ApiOperation(value = "get all courses", notes = "获取所有课程api")
    @ApiImplicitParam(name = "curPage", value = "当前是第几页", required = true, dataType = "Integer", paramType = "query")
    @PostMapping("/allCourses")
    public CommonReturnType allCourses(Integer curPage) throws BusinessException {
        if (curPage == null) {
            curPage = 1;
        }
        PageResult result = courseService.getAllCourses(curPage, DEFAULT_PAGE_SIZE);
        return CommonReturnType.create(result);
    }*/

    @ApiOperation(value = "get searched courses", notes = "搜索课程api")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "curPage", value = "当前是第几页", required = true,
                dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "searchKey", value = "要搜索的内容", required = true,
                    dataType = "string", paramType = "query")
    })
    @PostMapping("/searchCourse")
    public CommonReturnType searchCourse(Integer curPage, String searchKey) {
        if (curPage == null) {
            curPage = 1;
        }
        if (StringUtils.isBlank(searchKey)) {
            searchKey = "";
        }
        PageResult result = courseService.getCoursesBySearchKey(searchKey, curPage, DEFAULT_PAGE_SIZE);
        // 如果搜索内容不为空，添加搜索记录
        if (StringUtils.isNotBlank(searchKey)) {
            SearchRecord record = new SearchRecord();
            record.setId(sid.nextShort());
            record.setContent(searchKey);
            record.setCreateDate(new Date());
            searchRecordService.addSearchRecord(record);
        }
        return CommonReturnType.create(result);
    }

    @ApiOperation(value = "get latest courses", notes = "获取最新课程api")
    @ApiImplicitParam(name = "numOfRecord", value = "获取的记录条数", required = true, dataType = "Integer", paramType = "query")
    @PostMapping("/getLatestCourses")
    public CommonReturnType getLatestCourses(Integer numOfRecords) throws BusinessException {
        List<CourseVO> list = courseService.selectCoursesOrderByCreateTime(numOfRecords);
        return CommonReturnType.create(list);
    }


    @ApiOperation(value = "get hottest courses", notes = "获取热门课程api")
    @ApiImplicitParam(name = "numOfRecord", value = "获取的记录条数", required = true, dataType = "Integer", paramType = "query")
    @PostMapping("/getHottestCourses")
    public CommonReturnType getHottestCourses(Integer numOfRecords) throws BusinessException {
        List<CourseVO> list = courseService.selectHottestCourses(numOfRecords);
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "get recommended courses", notes = "根据用户历史记录获取推荐课程api")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "numOfRecord", value = "获取的记录条数",
                    required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id",
                    required = false, dataType = "string", paramType = "query")
    })
    @PostMapping("/getRecommendedCourses")
    public CommonReturnType getRecommendedCourses(String userId, Integer numOfRecords) throws BusinessException {
        List<CourseVO> list = null;
        // 用户没有浏览记录，返回空
        List<String> history = redis.lrange(USER_COURSE_HISTORY + ":" + userId, 0, -1);
        if (history == null || history.size() == 0) {
            return CommonReturnType.create(null);
        }
        if (numOfRecords == null || numOfRecords == 0) {
            numOfRecords = 4;
        }
        // 根据浏览记录推荐
        String courseId = history.get(0);
        list = courseService.selectRecommendCourses(courseId, numOfRecords);
        return CommonReturnType.create(list);
    }


    @ApiOperation(value = "get course by id", notes = "根据课程id获取课程api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/queryCourse")
    public CommonReturnType queryCourse(String courseId) throws BusinessException {
        Course course = courseService.selectCourseById(courseId);
        return CommonReturnType.create(course);
    }

    @ApiOperation(value = "update course study count by id", notes = "更新课程学习次数api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/updateStudyCount")
    public CommonReturnType updateStudyCount(String courseId) throws BusinessException {
        Course course = courseService.selectCourseById(courseId);
        if (course == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在");
        }
        course.setStudyCount(course.getStudyCount() + 1);
        courseService.updateCourse(course);
        return CommonReturnType.create(course);
    }

}
