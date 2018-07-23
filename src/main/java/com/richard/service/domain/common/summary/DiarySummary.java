package com.richard.service.domain.common.summary;

import com.richard.service.domain.diary.DiaryTag;
import com.richard.service.domain.diary.Praise;
import com.richard.service.domain.user.User;

import java.util.Date;

public interface DiarySummary {
     long getId();
     String getTitle();
     String getContent();
     User getUser();
     String getPicture();
     int getStatus();
     Date getUpdateTime();
     Date getCreateTime();
     DiaryTag getDiaryTag();
     int getReadNum();
     int getShareNum();
     int getIsPraise();
}
