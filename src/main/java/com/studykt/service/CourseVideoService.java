package com.studykt.service;

import com.studykt.entity.CourseVideo;

import java.util.List;

public interface CourseVideoService {

    int addCourseVideo(CourseVideo courseVideo);

    List<CourseVideo> getVideosByCourseId(String courseId);
}
