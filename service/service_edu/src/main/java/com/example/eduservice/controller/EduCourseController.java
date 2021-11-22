package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseService;
import com.example.servicebase.exceptionHandler.GuliExceptrion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //课程列表 TODO
    //完善成条件查询带分页

    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) throws GuliExceptrion {
        String id=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }


    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程信息
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

