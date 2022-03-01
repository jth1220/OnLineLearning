package com.example.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.SubjectData;
import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.entity.subject.TwoSubject;
import com.example.eduservice.listener.SubjectExcelListener;
import com.example.eduservice.mapper.EduSubjectMapper;
import com.example.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Jiangth
 * @since 2021-11-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile multipartFile,EduSubjectService eduSubjectService) {
        try {
            InputStream in=multipartFile.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询二级分类
        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建list集合。用于存储最终封装数据
        List<OneSubject> finalSubjectList=new ArrayList<>();
        //封装一级分类
        //查询出来的一级分类吉和便利，得到每个一级分类对象，获取每个一级分类对象值。
        for (EduSubject eduSubject : oneSubjectList) {
            //把edusubject中的值或取出来放到onesubject中
            OneSubject oneSubject=new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);
            //在一级分类中的循环再查询所有的二级分类
            List<TwoSubject> twoFinalSubjectList=new ArrayList<>();
            for (EduSubject tSubject : twoSubjectList) {
                if(tSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject=new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }


}
