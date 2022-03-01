package com.example.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.MD5;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.entity.vo.RegisterVo;
import com.example.educenter.mapper.UcenterMemberMapper;
import com.example.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.servicebase.exceptionHandler.GuliExceptrion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-25
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    //登陆方法
    @Override
    public String login(UcenterMember member) {
        //获取登陆手机号和密码
        String mobile=member.getMobile();
        String password=member.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            try {
                throw new GuliExceptrion(20001,"登录失败");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember==null){//没有这个手机号
            try {
                throw new GuliExceptrion(20001,"手机号不存在");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        //因为存储到数据库中的密码是加密的
        //将输入的密码进行加密，再和数据库密码进行比较
        //加密方法MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            try {
                throw new GuliExceptrion(20001,"密码错误");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        if(mobileMember.getIsDisabled()){
            try {
                throw new GuliExceptrion(20001,"用户被禁用");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }
    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||
                StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            try {
                throw new GuliExceptrion(20001,"登录失败");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }

        //判断手机验证码是否正确
        //获取redis中的验证码
        String redisCode =redisTemplate.opsForValue().get(mobile).toString();
        if(!code.equals(redisCode)){
            try {
                throw new GuliExceptrion(20001,"验证码错误");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer c = baseMapper.selectCount(wrapper);
        if(c>0){
            try {
                throw new GuliExceptrion(20001,"手机号已存在");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member=baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
