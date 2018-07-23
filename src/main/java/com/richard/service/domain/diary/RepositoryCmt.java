package com.richard.service.domain.diary;

import com.richard.service.domain.common.summary.CommentSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * by Richard on 2017/9/10
 * desc:
 */
public interface RepositoryCmt extends JpaRepository<Comment,Long> {
    Page findCommentByResourceIdAndStatusNotAndToCommentIdNotNull(long diaryId, int status, Pageable pageable);

    Page findCommentByResourceIdAndStatusNot(long diaryId,int status, Pageable pageable);
}
