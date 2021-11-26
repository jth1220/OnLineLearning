package com.example.educenter.controller;


import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.entity.vo.RegisterVo;
import com.example.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-25
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    UcenterMemberService memberService;

    //登陆
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        String token=memberService.login(member);
        return R.ok().data("token",token);
    }


    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request获取头信息。返回用户id
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


}

