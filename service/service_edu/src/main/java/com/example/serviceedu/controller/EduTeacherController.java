package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
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
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //2.逻辑删除讲师的方法
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }
        return R.error();
    }

    //分页查询讲师的方法 current 当前页  limit 每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        //创建配置对象
        Page<EduTeacher> pageTeacher=new Page<>(1,3);
        //调用方法的时候会将分页的所有数据封装到PageTeacher中
        eduTeacherService.page(pageTeacher,null);
        long total=pageTeacher.getTotal();
        List<EduTeacher> list=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }





}

