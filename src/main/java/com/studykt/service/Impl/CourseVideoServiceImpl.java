package com.studykt.service.Impl;

import com.studykt.entity.CourseExample;
import com.studykt.entity.CourseVideo;
import com.studykt.entity.CourseVideoExample;
import com.studykt.mapper.CourseVideoMapper;
import com.studykt.service.CourseVideoService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    @Autowired
    private CourseVideoMapper courseVideoMapper;

    @Autowired
    private Sid sid;

    @Override
    public int addCourseVideo(CourseVideo courseVideo) {
        if (courseVideo == null) {
            return -1;
        }
        courseVideo.setId(sid.nextShort());
        courseVideoMapper.insertSelective(courseVideo);
        return 0;
    }

    @Override
    public List<CourseVideo> getVideosByCourseId(String courseId) {
        if (StringUtils.isBlank(courseId)) {
            return null;
        }
        CourseVideoExample example = new CourseVideoExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseVideo> list = courseVideoMapper.selectByExample(example);
        return list;
    }
}
