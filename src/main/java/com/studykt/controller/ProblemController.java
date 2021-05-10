package com.studykt.controller;


import com.studykt.controller.vo.ProblemVO;
import com.studykt.controller.vo.WrongProblemsVO;
import com.studykt.entity.Problem;
import com.studykt.entity.ProblemOption;
import com.studykt.entity.TestResult;
import com.studykt.error.BusinessError;
import com.studykt.error.BusinessException;
import com.studykt.response.CommonReturnType;
import com.studykt.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Api(value = "api for problem", tags = {"试题相关接口"})
@RestController
@RequestMapping("/problem")
public class ProblemController extends BaseController {

    @Autowired
    private ProblemService problemService;


    @ApiOperation(value = "get problems records from file", notes = "获取某个学科的试题信息api")
    @ApiImplicitParam(name="category", value="学科分类名", required=true, dataType="string", paramType="query")
    @PostMapping("/getProblems")
    public CommonReturnType getProblems(String category) {
        List<ProblemVO> list = new ArrayList<>();
        List<Problem> problemList = problemService.selectByCategory(category);
        if (problemList == null) {
            return CommonReturnType.create(null);
        }
        for (Problem p : problemList) {
            ProblemVO problemVO = new ProblemVO();
            BeanUtils.copyProperties(p, problemVO);
            problemVO.setOptions(problemService.selectOptionContentByProblemId(p.getId()));
            list.add(problemVO);
        }
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "add test result", notes = "添加用户考试记录api")
    @PostMapping("/addTestResult")
    public CommonReturnType addTestResult(@RequestBody TestResult testResult) throws BusinessException {
        if (testResult == null || StringUtils.isBlank(testResult.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 记录测试结果
        testResult.setId(sid.nextShort());
        testResult.setCreateDate(new Date());
        problemService.addTestResult(testResult);
        return CommonReturnType.create("添加成功！");
    }

    @ApiOperation(value = "delete test result", notes = "删除用户考试记录api")
    @ApiImplicitParam(name = "id", value = "考试记录id", required = true, dataType = "string", paramType = "query")
    @PostMapping("/deleteTestResult")
    public CommonReturnType deleteTestResult(String id) throws BusinessException {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 删除记录
        problemService.deleteTestResult(id);
        return CommonReturnType.create("删除成功！");
    }

    @ApiOperation(value = "add wrong problem ids", notes = "添加用户错误试题api")
    @PostMapping("/addWrong")
    public CommonReturnType addWrong(@RequestBody WrongProblemsVO wrongProblemsVO) throws BusinessException {
        if (wrongProblemsVO == null) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (wrongProblemsVO.getWrongIds() == null) {
            wrongProblemsVO.setWrongIds(new ArrayList<String>());
        }
        if (StringUtils.isBlank(wrongProblemsVO.getUserId())) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<String> ids = wrongProblemsVO.getWrongIds();
        String userId = wrongProblemsVO.getUserId();
        String key = USER_WRONG_PROBLEMS + ":" + userId;
        for (String problemId: ids) {
            redis.removeValue(key, problemId);
            redis.leftPush(key, problemId);
            // 设置过期时间一个月
            redis.expire(key, BASE_REDIS_TIMEOUT * 24 * 30);
        }
        return CommonReturnType.create("添加成功！");
    }

    @ApiOperation(value = "get test results", notes = "获取用户考试记录api")
    @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="string", paramType="query")
    @PostMapping("/getTestResult")
    public CommonReturnType getTestResult(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<TestResult> list = problemService.selectResultByUserId(userId);
        return CommonReturnType.create(list);
    }

    @ApiOperation(value = "get wrong problems records from redis", notes = "获取用户错题api")
    @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="string", paramType="query")
    @PostMapping("/getWrong")
    public CommonReturnType getWrong(String userId) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(BusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String key = USER_WRONG_PROBLEMS + ":" + userId;
        List<String> wrongIds = redis.lrange(key, 0, -1);
        List<ProblemVO> list = new ArrayList<>();
        for (String wrongId : wrongIds) {
            Problem p = problemService.selectByProblemId(wrongId);
            if (p != null) {
                list.add(convertFromDO2VO(p));
            }
        }
        return CommonReturnType.create(list);
    }





    @ApiOperation(value = "import problem records from file", notes = "导入试题信息api")
    @ApiImplicitParam(name="filePath", value="文件路径", required=true, dataType="string", paramType="query")
    @PostMapping("/importProblems")
    public void importProblems(String filePath) {
        List<Problem> problemList = new ArrayList<>();
        List<ProblemOption> problemOptions = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream(filePath));
            int m = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < m; i++) {
                String category = sc.nextLine();
                int n = Integer.parseInt(sc.nextLine());
                for (int j = 0; j < n; j++) {
                    Problem p = new Problem();
                    p.setId(sid.nextShort());
                    p.setpIndex(Integer.parseInt(sc.nextLine()));
                    p.setContent(sc.nextLine());
                    p.setCategory(category);
                    String [] options = sc.nextLine().split("\\|");
                    for (int k = 0; k < options.length; k++) {
                        ProblemOption option = new ProblemOption();
                        option.setId(sid.nextShort());
                        option.setProblemId(p.getId());
                        option.setContent(options[k]);
                        option.setOptIndex(k);
                        problemOptions.add(option);
                    }
                    p.setType(0);
                    p.setAnoIndex(Integer.parseInt(sc.nextLine()));
                    p.setAnalysis(sc.nextLine());
                    p.setBrowseCount(0);
                    problemList.add(p);
                }
            }
            /*for (Problem p : problemList) {
                System.out.println(p.toString());
            }*/
            problemService.addProblems(problemList);
            problemService.addProblemOptions(problemOptions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    private ProblemVO convertFromDO2VO(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        problemVO.setOptions(problemService.selectOptionContentByProblemId(problem.getId()));
        return problemVO;
    }

}
