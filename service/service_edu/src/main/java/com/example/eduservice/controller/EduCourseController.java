package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseService;
import com.example.servicebase.exceptionHandler.GuliExceptrion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) throws GuliExceptrion {
        String id=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

}

