package com.example.eduservice.controller;


import com.example.commonutils.R;
import com.example.eduservice.client.VodClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.service.EduVideoService;
import com.example.servicebase.exceptionHandler.GuliExceptrion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO  删除小节的时候删除视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获得视频id
        String vid=videoService.getById(id).getVideoSourceId();
        if(!StringUtils.isEmpty(vid)){
            R r = vodClient.removeAlyVideo(vid);
            if(r.getCode()==20001){
                try {
                    throw new GuliExceptrion(20001,"熔断器问题");
                } catch (GuliExceptrion guliExceptrion) {
                    guliExceptrion.printStackTrace();
                }
            }
        }
        videoService.removeById(id);
        return R.ok();
    }
    //修改小节 TODO
}

