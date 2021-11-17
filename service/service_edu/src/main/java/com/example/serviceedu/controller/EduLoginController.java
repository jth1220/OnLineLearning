package com.example.serviceedu.controller;

import com.example.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/eduservice/user")
@CrossOrigin(allowCredentials = "true")
public class EduLoginController {

    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img2.baidu.com/it/u=1945464906,1635022113&fm=26&fmt=auto");
    }

}
