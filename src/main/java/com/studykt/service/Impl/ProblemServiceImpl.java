package com.studykt.service.Impl;


import com.studykt.controller.vo.ProblemVO;
import com.studykt.entity.*;
import com.studykt.mapper.ProblemMapper;
import com.studykt.mapper.ProblemOptionMapper;
import com.studykt.mapper.TestResultMapper;
import com.studykt.service.ProblemService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
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
    public List<Problem> selectAll() {
        return problemMapper.selectAll();
    }

    @Override
    public int addProblem(Problem problem) {
        return problemMapper.insertSelective(problem);
    }

    @Override
    public int addProblem(ProblemVO problemVO) {
        Problem problem = convertFormProblemVO2DO(problemVO);
        if (problem != null) {
            problemMapper.insertSelective(problem);
            addProblemOptionsFormVO(problemVO);
        }
        return 0;
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
    public int updateProblem(Problem problem) {
        return problemMapper.updateByPrimaryKey(problem);
    }

    @Override
    public int updateProblem(ProblemVO problemVO) {
        Problem problem = convertFormProblemVO2DO(problemVO);
        if (problem == null) {
            return 0;
        }
        deleteProblemOptionsByProblemId(problemVO.getId());
        addProblemOptionsFormVO(problemVO);
        problemMapper.updateByPrimaryKey(problem);
        return 0;
    }

    private void addProblemOptionsFormVO(ProblemVO problemVO) {
        List<String> options = problemVO.getOptions();
        if (options != null) {
            Sid sid = new Sid();
            for (int i = 0; i < options.size(); i++) {
                ProblemOption option = new ProblemOption();
                option.setId(sid.nextShort());
                option.setOptIndex(i);
                option.setContent(options.get(i));
                option.setProblemId(problemVO.getId());
                addProblemOption(option);
            }
        }
    }

    @Override
    public int addProblemOption(ProblemOption problemOption) {
        return problemOptionMapper.insertSelective(problemOption);
    }

    @Override
    public int addProblemOptions(List<ProblemOption> problemOptions) {
        if (problemOptions != null) {
            for (ProblemOption po : problemOptions) {
                problemOptionMapper.insertSelective(po);
            }
        }
        return 0;
    }

    @Override
    public int deleteProblemOptionsByProblemId(String problemId) {
        if (StringUtils.isBlank(problemId)) {
            return 0;
        }
        ProblemOptionExample example = new ProblemOptionExample();
        example.createCriteria().andProblemIdEqualTo(problemId);
        return problemOptionMapper.deleteByExample(example);
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

    private Problem convertFormProblemVO2DO(ProblemVO problemVO) {
        if (problemVO == null || StringUtils.isBlank(problemVO.getId())) {
            return null;
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemVO, problem);
        return problem;
    }

}
