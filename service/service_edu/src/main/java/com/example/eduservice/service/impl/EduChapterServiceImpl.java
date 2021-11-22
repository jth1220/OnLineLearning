package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.chapter.ChapterVo;
import com.example.eduservice.entity.chapter.VideoVo;
import com.example.eduservice.mapper.EduChapterMapper;
import com.example.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import com.example.servicebase.exceptionHandler.GuliExceptrion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询所有章节
        QueryWrapper<EduChapter> wrapperChapter=new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        //查询所有小节
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        //遍历章节list封装在遍历小节list封装
        List<ChapterVo> finalList = new ArrayList<>();
        //遍历章节集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);
            List<VideoVo> videoList=new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo=eduVideoList.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    //删除章节的方法，如果章节下有小节，则不删，否则删除
    @Override
    public boolean deleteChapterById(String chapterId) {
        //根据章节id查询小节表。如果能查到则不删除
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count>0){
            try {
                throw new GuliExceptrion(20001,"有小节，不能删除");
            } catch (GuliExceptrion guliExceptrion) {
                guliExceptrion.printStackTrace();
            }
        }
        int result=baseMapper.deleteById(chapterId);
        return result>0;
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

}
