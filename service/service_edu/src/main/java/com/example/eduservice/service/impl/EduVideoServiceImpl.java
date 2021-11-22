package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.mapper.EduVideoMapper;
import com.example.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    //根据课程id删除小节
    //TODO 删除小节对应删除视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
