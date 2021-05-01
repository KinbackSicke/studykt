package com.studykt.service.Impl;


import com.studykt.entity.*;
import com.studykt.mapper.ProblemMapper;
import com.studykt.mapper.ProblemOptionMapper;
import com.studykt.mapper.TestResultMapper;
import com.studykt.service.ProblemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemOptionMapper problemOptionMapper;

    @Autowired
    private TestResultMapper testResultMapper;

    @Override
    public Problem selectByProblemId(String problemId) {
        return problemMapper.selectByPrimaryKey(problemId);
    }

    @Override
    public List<Problem> selectByCategory(String category) {
        if (StringUtils.isBlank(category)) {
            category = "";
        }
        ProblemExample example = new ProblemExample();
        example.createCriteria().andCategoryLike("%" + category + "%");
        return problemMapper.selectByExample(example);
    }

    @Override
    public int addProblem(Problem problem) {
        return problemMapper.insertSelective(problem);
    }

    @Override
    public int addProblems(List<Problem> problemList) {
        for (Problem p : problemList) {
            problemMapper.insertSelective(p);
        }
        return 0;
    }

    @Override
    public int deleteProblemsById(String problemId) {
        if (StringUtils.isBlank(problemId)) {
            return -1;
        }
        return problemMapper.deleteByPrimaryKey(problemId);
    }

    @Override
    public int deleteProblemsByCategory(String category) {
        ProblemExample example = new ProblemExample();
        example.createCriteria().andCategoryLike("%" + category + "%");
        return problemMapper.deleteByExample(example);
    }

    @Override
    public int addProblemOption(ProblemOption problemOption) {
        return problemOptionMapper.insertSelective(problemOption);
    }

    @Override
    public int addProblemOptions(List<ProblemOption> problemOptions) {
        for (ProblemOption po : problemOptions) {
            problemOptionMapper.insertSelective(po);
        }
        return 0;
    }

    @Override
    public List<String> selectOptionContentByProblemId(String problemId) {
        if (StringUtils.isBlank(problemId)) {
            return null;
        }
        return problemOptionMapper.selectContentByProblemId(problemId);
    }

    @Override
    public List<TestResult> selectResultByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        TestResultExample example = new TestResultExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return testResultMapper.selectByExample(example);
    }

    @Override
    public int addTestResult(TestResult testResult) {
        return testResultMapper.insertSelective(testResult);
    }

    @Override
    public int deleteTestResult(String id) {
        if (StringUtils.isBlank(id)) {
            return -1;
        }
        return testResultMapper.deleteByPrimaryKey(id);
    }


}
