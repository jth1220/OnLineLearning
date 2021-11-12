package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.vo.TeacherQuery;
import com.example.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.sql.SQLException;
import java.sql.Wrapper;
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
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //调用方法的时候会将分页的所有数据封装到PageTeacher中
        eduTeacherService.page(pageTeacher,null);
        long total=pageTeacher.getTotal();
        List<EduTeacher> list=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        // 创建配置对象
        Page<EduTeacher> pageTeacher=new Page<>(1,3);
        // 构建条件,判断条件是否为空 不为空则拼接
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        eduTeacherService.page(pageTeacher, queryWrapper);
        long total=pageTeacher.getTotal();
        List<EduTeacher> list=pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",list);
    }

    //添加讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean sav = eduTeacherService.save(eduTeacher);
        if(sav){
            return R.ok();
        }
        return R.error();
    }

    //根据讲师ID进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    //根据id进行修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }
        return R.error();
    }

}

