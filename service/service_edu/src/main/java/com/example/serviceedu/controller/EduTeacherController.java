package com.example.serviceedu.controller;


import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-11
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/serviceedu/edu-teacher")
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService eduTeacherService;

    //1.查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }

    //2.逻辑删除讲师的方法
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        return b;
    }







}

