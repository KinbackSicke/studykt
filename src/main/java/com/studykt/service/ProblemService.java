package com.studykt.service;

import com.studykt.entity.Problem;
import com.studykt.entity.ProblemOption;
import com.studykt.entity.TestResult;

import java.util.List;

public interface ProblemService {

    Problem selectByProblemId(String problemId);

    List<Problem> selectByCategory(String category);

    int addProblem(Problem problem);

    int addProblems(List<Problem> problemList);

    int deleteProblemsById(String problemId);

    int deleteProblemsByCategory(String category);

    int addProblemOption(ProblemOption problemOption);

    int addProblemOptions(List<ProblemOption> problemOptions);

    List<String> selectOptionContentByProblemId(String problemId);

    List<TestResult> selectResultByUserId(String userId);

    int addTestResult(TestResult testResult);

    int deleteTestResult(String id);
}
