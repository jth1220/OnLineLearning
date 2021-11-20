package com.example.eduservice.service;

import com.example.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.servicebase.exceptionHandler.GuliExceptrion;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo) throws GuliExceptrion;
}
