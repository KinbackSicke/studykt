package com.studykt.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.studykt.controller.vo.CourseVO;
import com.studykt.entity.Course;
import com.studykt.entity.CourseExample;
import com.studykt.mapper.CourseMapper;
import com.studykt.mapper.CourseMapperCustom;
import com.studykt.service.CourseService;
import com.studykt.utils.PageResult;
import io.swagger.annotations.Example;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseMapperCustom courseMapperCustom;

    @Autowired
    private Sid sid;

    @Override
    public List<Course> selectCourseByTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andTitleEqualTo(title);
        List<Course> list = courseMapper.selectByExample(courseExample);
        return list;
    }

    @Override
    public int addCourse(Course course) {
        if (course == null) {
            return -1;
        }
        course.setId(sid.nextShort());
        courseMapper.insertSelective(course);
        return 0;
    }

    @Override
    public List<CourseVO> selectCoursesOrderByCreateTime(Integer numOfRecords) {
        return courseMapperCustom.selectByCreateDateDESC(numOfRecords);
    }

    @Override
    public List<CourseVO> selectRecommendCourses(String courseId, Integer numOfRecords) {
        if (StringUtils.isBlank(courseId)) {
            return null;
        }
        if (numOfRecords == null) {
            numOfRecords = 4;
        }
        return courseMapperCustom.selectRecommendedById(courseId, numOfRecords);
    }

    @Override
    public Course selectCourseById(String courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public CourseVO selectCourseVOById(String courseId) {
        return courseMapperCustom.selectCourseById(courseId);
    }

    @Override
    public int updateCourse(Course course) {
        if (course == null) {
            return -1;
        }
        CourseExample example = new CourseExample();
        example.createCriteria().andIdEqualTo(course.getId());
        courseMapper.updateByExampleSelective(course, example);
        return 0;
    }

    @Override
    public int updateCourseById(Course course) {
        if (course == null) {
            return -1;
        } else if (StringUtils.isBlank(course.getId())) {
            return -1;
        }
        courseMapper.updateByPrimaryKeySelective(course);
        return 0;
    }

    @Override
    public int deleteCourseById(String courseId) {
        return courseMapper.deleteByPrimaryKey(courseId);
    }

    @Override
    public Integer selectCourseFavorCount(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            return 0;
        }
        return courseMapperCustom.selectFavorCount(courseId);
    }

    @Override
    public List<CourseVO> getAllCourses() {
        // 使用pageHelper实现分页 curPage: 当前所在页，pageSize：页面大小

        List<CourseVO> list = courseMapperCustom.selectAllCourses();
        return list;
    }

    @Override
    public PageResult getCoursesBySearchKey(String searchKey, Integer curPage, Integer pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<CourseVO> list = courseMapperCustom.selectByTitleAndTagName('%' + searchKey + '%');
        return generatePageResult(list, curPage);
    }

    @Override
    public PageResult getCoursesByTagName(String searchKey, Integer curPage, Integer pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<CourseVO> list = courseMapperCustom.selectByTagName('%' + searchKey + '%');
        return generatePageResult(list, curPage);
    }

    @Override
    public List<CourseVO> selectHottestCourses(Integer numOfRecords) {
        if (numOfRecords == null) {
            numOfRecords = 4;
        }
        return courseMapperCustom.selectByStudyCountDESC(numOfRecords);
    }

    private PageResult generatePageResult(List<CourseVO> list, Integer curPage) {
        PageInfo<CourseVO> pageList = new PageInfo<>(list);
        PageResult pageResult = new PageResult();
        pageResult.setPage(curPage);
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

}
