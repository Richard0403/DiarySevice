package com.richard.service.domain.diary;

import com.richard.service.domain.common.summary.DiarySummary;
import com.richard.service.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * by Richard on 2017/9/10
 * desc:
 */
public interface RepositoryDiary extends JpaRepository<Diary,Long>,JpaSpecificationExecutor<Diary> {

    Page<Diary> findByUserAndStatusNot(User user, int status, Pageable pageable);

    Page<Diary> findByStatusNot(int status, Pageable pageable);

    long countByDiaryTagId(long tagId);

    @Query("select " +
            "diary.id as id," +
            "diary.title as title," +
            "diary.content as content," +
            "diary.user as user," +
            "diary.picture as picture," +
            "diary.status as status," +
            "diary.updateTime as updateTime," +
            "diary.createTime as createTime," +
            "diary.diaryTag as diaryTag," +
            "diary.status as status, " +
            "diary.readNum as readNum, " +
            "diary.shareNum as shareNum, " +
            "count(praiseSet) as isPraise "+
            "from Diary as diary " +
            "left outer join diary.praises as praiseSet " +
            "on praiseSet.user.id = ?1 "+
            "where diary.diaryTag.id = ?2  group by diary.id " +
            "order by diary.createTime desc ")
    Page<DiarySummary> getDiarys(long userId, long diaryTagId, Pageable pageable);

}
