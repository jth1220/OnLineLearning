package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.client.VodClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.mapper.EduVideoMapper;
import com.example.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    public VodClient vodClient;

    //根据课程id删除小节并删除视频
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        List<String> list = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoSourseId=eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourseId)){
                list.add(videoSourseId);
            }
        }
        if(!list.isEmpty()) vodClient.deleteBatch(list);
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
