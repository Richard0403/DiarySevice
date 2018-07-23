package com.richard.service.domain.diary;

import com.richard.service.domain.common.summary.DiaryTagSummary;
import com.richard.service.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * by Richard on 2017/9/10
 * desc:
 */
public interface RepositoryDiaryTag extends JpaRepository<DiaryTag,Long>{

    @Query("select " +
            "count(diary) as diaryCount,"+
            "tag.user as user," +
            "tag.id as id," +
            "tag.name as name," +
            "tag.description as description," +
            "tag.updateTime as updateTime," +
            "tag.createTime as createTime," +
            "tag.picture as picture," +
            "tag.status as status " +
            "from DiaryTag as tag " +
            "left outer join tag.diarySet as diary " +
            "on diary.status <> -1 "+
            "where tag.user.id = ?1 group by tag.id " +
            "order by tag.updateTime desc ")
    Page<DiaryTagSummary> getUserDiaryWithCount(long userId, Pageable pageable);

//    @Query(value = "select" +
//            " tag.user_id ," +
//            " tag.id ," +
//            " tag.name ," +
//            " tag.description ," +
//            " tag.update_time ," +
//            " tag.create_time ," +
//            " tag.picture," +
//            " tag.status," +
//            " count(diary.id) as diaryCount" +
//            " from diary_tag as tag" +
//            " left join diary" +
//            " on tag.id = diary.tag_id" +
//            " where tag.user_id = ?1 and diary.status <> -1" +
//            " group by tag_id" +
//            " ORDER BY ?#{#pageable}",
//            countQuery = "select count(*) from diary_tag  where diary_tag.user_id = ?1",nativeQuery = true)
//    Page<DiaryTagSummary> getUserDiaryWithCount(long userId, Pageable pageable);




}
