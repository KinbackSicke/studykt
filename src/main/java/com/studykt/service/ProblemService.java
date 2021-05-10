package com.studykt.service;

import com.studykt.controller.vo.ProblemVO;
import com.studykt.entity.Problem;
import com.studykt.entity.ProblemOption;
import com.studykt.entity.TestResult;

import java.util.List;

public interface ProblemService {

    Problem selectByProblemId(String problemId);

    List<Problem> selectByCategory(String category);

    List<Problem> selectAll();

    int addProblem(Problem problem);

    int addProblem(ProblemVO problemVO);

    int addProblems(List<Problem> problemList);

    int deleteProblemsById(String problemId);

    int deleteProblemsByCategory(String category);

    int updateProblem(Problem problem);

    int updateProblem(ProblemVO problemVO);

    int addProblemOption(ProblemOption problemOption);

    int addProblemOptions(List<ProblemOption> problemOptions);

    int deleteProblemOptionsByProblemId(String problemId);

    List<String> selectOptionContentByProblemId(String problemId);

    List<TestResult> selectResultByUserId(String userId);

    int addTestResult(TestResult testResult);

    int deleteTestResult(String id);
}
