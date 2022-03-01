package com.example.educenter.service;

import com.example.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-25
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegister(String day);
}
