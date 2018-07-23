package com.richard.service.service;

import com.mysql.jdbc.StringUtils;
import com.richard.service.domain.diary.*;
import com.richard.service.domain.user.RepositoryUser;
import com.richard.service.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * by Richard on 2017/9/10
 * desc:
 */
@Service
public class CmtService {

    @Autowired
    private RepositoryDiary repositoryDiary;
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryCmt repositoryCmt;
    @Autowired
    private RepositoryPraise repositoryPraise;


    public boolean commentDiary(long diaryId, String content, User user, String toCommentId, String toUserId) {
        try {
            Comment currentCmt = new Comment(content, diaryId, user);
            if(!StringUtils.isNullOrEmpty(toCommentId)){
                currentCmt.setToCommentId(Long.parseLong(toCommentId));
            }
            if(!StringUtils.isNullOrEmpty(toUserId)){
                User toUser = repositoryUser.findOne(Long.parseLong(toUserId));
                currentCmt.setToUser(toUser);
            }
            repositoryCmt.save(currentCmt);

            if(!StringUtils.isNullOrEmpty(toCommentId)){
                Comment parentCmt = repositoryCmt.findOne(Long.parseLong(toCommentId));
                parentCmt.getChildComment().add(currentCmt);
//                Set chiledCmts = parentCmt.getChildComment();
////                chiledCmts.add(currentCmt);
////                parentCmt.setChildComment(chiledCmts);
                repositoryCmt.save(parentCmt);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Comment> getComment(long diaryId, int pageSize, int pageNo){
        List<Comment> comments = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryCmt.findCommentByResourceIdAndStatusNotAndToCommentIdNotNull(diaryId, -1, pageable);
        comments.addAll(page.getContent());
        return comments;
    }

    public List<Comment> getSimpleComment(long resourceId, int pageSize, int pageNo){
        List<Comment> comments = new ArrayList<>();
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryCmt.findCommentByResourceIdAndStatusNot(resourceId, -1, pageable);
        comments.addAll(page.getContent());
        return comments;
    }


    public void delComment(Comment comment) {
        comment.setStatus(-1);
        repositoryCmt.save(comment);
    }

    public boolean praiseComment(User user, String commentId) {
        long cmtId = Long.parseLong(commentId);
        Comment comment = repositoryCmt.findOne(cmtId);
        Praise praise = repositoryPraise.findFirstByResourceIdAndUser(cmtId, user);
        if(praise==null){
            praise = new Praise(user, cmtId);
            comment.getPraises().add(praise);
            repositoryCmt.save(comment);
            return true;
        }else{
            comment.getPraises().remove(praise);
            repositoryCmt.save(comment);
            return false;
        }
    }
}
