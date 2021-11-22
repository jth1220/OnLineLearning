package com.example.eduservice.service;

import com.example.commonutils.R;
import com.example.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-19
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);

    void removeChapterByCourseId(String courseId);
}
