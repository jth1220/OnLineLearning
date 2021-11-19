package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-18
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取到上传过来的文件，将文件中的内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile multipartFile){
        subjectService.saveSubject(multipartFile,subjectService);
        return R.ok();
    }
}

