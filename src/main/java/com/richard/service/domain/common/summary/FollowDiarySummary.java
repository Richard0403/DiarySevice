package com.richard.service.domain.common.summary;

import com.richard.service.domain.diary.DiaryTag;
import com.richard.service.domain.user.User;

@Deprecated
public interface FollowDiarySummary {
     long id();
     long resourceId();
     int followType();
     long createTime();

     long diaryId();
     String title();
     String content();
     User user();
     String picture();
     DiaryTag diaryTag();
     int readNum();
     int shareNum();
}
