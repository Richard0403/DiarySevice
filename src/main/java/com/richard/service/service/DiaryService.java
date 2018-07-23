package com.richard.service.service;

import com.richard.service.domain.common.summary.DiarySummary;
import com.richard.service.domain.diary.*;
import com.richard.service.domain.user.RepositoryUser;
import com.richard.service.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * by Richard on 2017/9/10
 * desc:
 */
@Service
public class DiaryService {

    @Autowired
    private RepositoryDiary repositoryDiary;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryDiaryTag repositoryDiaryTag;
    @Autowired
    private RepositoryPraise repositoryPraise;

    public Map<String, Object> getUserDiary(User user, int pageNo, int pageSize){
        List<Diary> diarys = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryDiary.findByUserAndStatusNot(user,-1, pageable);
        int pageCount = page.getTotalPages();
        diarys.addAll(page.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("diary", diarys);
        map.put("pageCount",pageCount);
        return map;
    }

    public List<Diary> getUserDiary(int pageNo, int pageSize){
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryDiary.findByStatusNot(-1, pageable);
        return page.getContent();
    }


    public Diary getDiaryDetail(long id){
        Diary diary = repositoryDiary.findOne(id);
        return diary;
    }

    public Diary delDiary(Diary diary){
        diary.setStatus(-1);
        repositoryDiary.save(diary);
        return diary;
    }

    public Diary setDiaryStatus(Diary diary, int pubStatus){
        diary.setPublicStatus(pubStatus);
        repositoryDiary.save(diary);
        return diary;
    }

    public boolean addDiary(User user, String title, String content, int pubStatus, String picture, long diaryTagId) {
        if(user == null){
            return false;
        }
        DiaryTag diaryTag = repositoryDiaryTag.findOne(diaryTagId);
        Diary diary = new Diary(title,content,user, pubStatus, picture, diaryTag);
        diaryTag.getDiarySet().add(diary);
        repositoryDiaryTag.save(diaryTag);
        return true;
    }

    public void changeDiaryTag(Diary diary, DiaryTag diaryTag) {
        diary.setDiaryTag(diaryTag);
        repositoryDiary.save(diary);
    }

    /**
     *  复杂查询方式
     */
    public List<DiarySummary> getUserTagDiary(User user, DiaryTag diaryTag, int pageNo, int pageSize) {
//        List<Diary> diarys = new ArrayList<>();
//        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
//        Page<Diary> page = repositoryDiary.findAll(new Specification<Diary>() {
//            @Override
//            public Predicate toPredicate(Root<Diary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//                predicates.add(cb.equal(root.get("user").as(User.class), user));
//                predicates.add(cb.notEqual(root.get("status").as(Integer.class), -1));
//                predicates.add(cb.equal(root.get("diaryTag").as(DiaryTag.class), diaryTag));
//                Predicate[] pre = new Predicate[predicates.size()];
//                return  cb.and(predicates.toArray(pre));
//            }
//        }, pageable);
//        diarys.addAll(page.getContent());
        List<DiarySummary> diarys = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page<DiarySummary> page = repositoryDiary.getDiarys(user.getId(), diaryTag.getId(), pageable);
        diarys.addAll(page.getContent());
        return diarys;
    }

    public boolean praiseDiary(User user, String diaryId) {
        long dId = Long.parseLong(diaryId);
        Diary diary = repositoryDiary.findOne(dId);
        Praise praise = repositoryPraise.findFirstByResourceIdAndUser(dId, user);
        if(praise==null){
            praise = new Praise(user, dId);
            diary.getPraises().add(praise);
            repositoryDiary.save(diary);
            return true;
        }else{
            diary.getPraises().remove(praise);
            repositoryDiary.save(diary);
            return false;
        }
    }
}
