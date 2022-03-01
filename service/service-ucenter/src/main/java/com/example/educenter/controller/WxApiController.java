package com.example.educenter.controller;

import com.example.commonutils.JwtUtils;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.service.UcenterMemberService;
import com.example.educenter.utils.ConstantWxUtils;
import com.example.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    UcenterMemberService memberService;

    //获取扫描人信息,页面跳转
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            //获取code值，临时票据，类似于验证码
            //拿着code请求微信的固定地址，得到两个值 access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            //请求拼接好的地址得到返回的两个值Access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //吧accessTokenInfo字符串转成map集合，根据map里面的key获取对应值
            Gson gson=new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String  access_token = (String) mapAccessToken.get("access_token");
            String  openid = (String) mapAccessToken.get("openid");
            UcenterMember member=memberService.getOpenIdMember(openid);
            if(member==null){//如果member为空，则说明表里没有相同数据
                //拿着accesstoken和openid获取微信扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token,openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo中字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String  nickname = (String) userInfoMap.get("nickname");
                String  headimgurl = (String) userInfoMap.get("headimgurl");
                //将扫描人信息添加到数据库里面
                //判断数据库里面是否存在，根据openid来做判断
                member=new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //使用jwt根据member对象生成token字符串，通过路径传递token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:http://localhost:3000/login";
    }


    //生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect——url进行URLEncoding编码
        String redirectUrl=ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s内的值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "example");
        //重定向到请求微信地址
        return "redirect:"+url;

    }


}
