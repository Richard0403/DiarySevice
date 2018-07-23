package com.richard.service.service;

import com.mysql.jdbc.StringUtils;
import com.richard.service.domain.common.summary.DiaryTagSummary;
import com.richard.service.domain.diary.DiaryTag;
import com.richard.service.domain.diary.RepositoryDiary;
import com.richard.service.domain.diary.RepositoryDiaryTag;
import com.richard.service.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * by Richard on 2017/9/10
 * desc:
 */
@Service
public class DiaryTagService {

    @Autowired
    private RepositoryDiaryTag repositoryDiaryTag;
    @Autowired
    private RepositoryDiary repositoryDiary;


    public DiaryTag addDiaryTag(String name, String description, String picture, User user) {
        DiaryTag diaryTag = new DiaryTag(user, name, description, picture);
        repositoryDiaryTag.save(diaryTag);
        return diaryTag;
    }

    public DiaryTag fixDiaryTag(DiaryTag diaryTag, String name, String description, String picture) {
        if(!StringUtils.isNullOrEmpty(name)){
            diaryTag.setName(name);
        }
        if(!StringUtils.isNullOrEmpty(description)){
            diaryTag.setDescription(description);
        }
        if(!StringUtils.isNullOrEmpty(picture)){
            diaryTag.setPicture(picture);
        }
        repositoryDiaryTag.save(diaryTag);
        return diaryTag;
    }

    public DiaryTag delDiaryTag(DiaryTag diaryTag) {
        long count = repositoryDiary.countByDiaryTagId(diaryTag.getId());
        if(count == 0){
            //标签下不存在笔记，才可删除
            diaryTag.setStatus(-1);
            repositoryDiaryTag.save(diaryTag);
            return diaryTag;
        }else{
            return null;
        }
    }

    public List<DiaryTagSummary> getUserTag(User user, int pageNo, int pageSize) {
        List<DiaryTagSummary> diaryTags = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page<DiaryTagSummary> page = repositoryDiaryTag.getUserDiaryWithCount(user.getId(), pageable);
        diaryTags.addAll(page.getContent());
        return diaryTags;
    }
}
