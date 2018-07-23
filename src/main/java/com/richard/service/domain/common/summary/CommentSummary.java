package com.richard.service.domain.common.summary;

import com.richard.service.domain.diary.Comment;
import com.richard.service.domain.user.User;

import java.util.Date;
import java.util.List;

public interface CommentSummary {
     Comment comment();
     List<Comment> childComment();
}
