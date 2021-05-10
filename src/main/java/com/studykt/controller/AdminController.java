package com.studykt.controller;

import com.studykt.controller.vo.CourseVO;
import com.studykt.controller.vo.ProblemVO;
import com.studykt.entity.*;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@Api(value = "Api for admin operations", tags = {"管理员相关操作"})
@RequestMapping("/admin")
@CrossOrigin(allowCredentials="true", allowedHeaders="*")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCommentService courseCommentService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "query all users", notes = "获取所有用户信息")
    @PostMapping("/allUsers")
    public CommonReturnType getAllUsers() {
        List<User> users = adminService.selectAllUsers();
        return CommonReturnType.create(users);
    }


    @ApiOperation(value = "delete user by id", notes = "根据userId删除用户api")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/deleteUser")
    public CommonReturnType deleteUser(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户id不存在！");
        }
        userService.deleteUser(userId);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "query all courses", notes = "获取所有课程信息")
    @PostMapping("/allCourses")
    public CommonReturnType allCourses() {
        List<CourseVO> list = courseService.getAllCourses();
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "delete course by id", notes = "根据课程Id删除课程api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/deleteCourse")
    public CommonReturnType deleteCourse(String courseId) throws BusinessException {
        if (StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户id不存在！");
        }
        courseService.deleteCourseById(courseId);
        courseCommentService.deleteCommentByCourseId(courseId);
        return CommonReturnType.create("删除成功！");
    }

    @ApiOperation(value = "get a course by id", notes = "根据courseId查询课程api")
    @ApiImplicitParam(name = "courseId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getCourse")
    public CommonReturnType getCourse(String courseId) throws BusinessException {
        if (StringUtils.isBlank(courseId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "用户id不存在！");
        }
        CourseVO courseVO =  courseService.selectCourseVOById(courseId);
        return CommonReturnType.create(courseVO);
    }

    @ApiOperation(value = "update course by id", notes = "根据courseId更新课程api")
    @PostMapping("/updateCourse")
    public CommonReturnType updateCourse(@RequestBody Course course) throws BusinessException {
        courseService.updateCourseById(course);
        return CommonReturnType.create();
    }

    @ApiOperation(value = "add course by id", notes = "根据courseId课程api")
    @PostMapping("/addCourse")
    public CommonReturnType addCourse(@RequestBody Course course) throws BusinessException {
        if (course == null) {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
        course.setId(sid.nextShort());
        course.setStudyCount(0);
        course.setCreateDate(new Date());
        courseService.addCourse(course);
        return CommonReturnType.create("添加成功！");
    }

    @ApiOperation(value = "query all problems", notes = "获取所有试题信息")
    @PostMapping("/allProblems")
    public CommonReturnType allProblems() {
        List<Problem> problemList = problemService.selectAll();
        return CommonReturnType.create(problemList);
    }

    @ApiOperation(value = "get a problem by id", notes = "根据problemId查询试题api")
    @ApiImplicitParam(name = "problemId", value = "课程id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/getProblem")
    public CommonReturnType getProblem(String problemId) throws BusinessException {
        if (StringUtils.isBlank(problemId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程id不存在！");
        }
        Problem problem = problemService.selectByProblemId(problemId);
        if (problem == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程不存在！");
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        problemVO.setOptions(problemService.selectOptionContentByProblemId(problemId));
        return CommonReturnType.create(problemVO);
    }

    @ApiOperation(value = "update problem", notes = "更新试题信息")
    @PostMapping("/updateProblem")
    public CommonReturnType updateProblem(@RequestBody ProblemVO problemVO) throws BusinessException {
        if (problemVO == null || StringUtils.isBlank(problemVO.getId())) {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
        problemService.updateProblem(problemVO);
        return CommonReturnType.create("更新成功！");
    }

    @ApiOperation(value = "add problem", notes = "添加试题信息")
    @PostMapping("/addProblem")
    public CommonReturnType addProblem(@RequestBody ProblemVO problemVO) throws BusinessException {
        if (problemVO == null) {
            throw new BusinessException(BusinessError.UNKNOWN_ERROR);
        }
        problemVO.setBrowseCount(0);
        problemVO.setId(sid.nextShort());
        problemService.addProblem(problemVO);

        return CommonReturnType.create("添加成功！");
    }

    @ApiOperation(value = "delete problem", notes = "删除试题")
    @ApiImplicitParam(name = "problemId", value = "试题ID", required = true, dataType = "string", paramType = "query")
    @PostMapping("deleteProblem")
    public CommonReturnType deleteProblem(String problemId) throws BusinessException {
        if (StringUtils.isBlank(problemId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR, "课程ID不存在！");
        }
        problemService.deleteProblemsById(problemId);
        // 删除题目相关选项
        problemService.deleteProblemOptionsByProblemId(problemId);
        return CommonReturnType.create("删除成功！");
    }

}
