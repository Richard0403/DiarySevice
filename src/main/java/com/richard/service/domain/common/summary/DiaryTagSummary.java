package com.richard.service.domain.common.summary;

import com.richard.service.domain.user.User;

import java.util.Date;

public interface DiaryTagSummary {
     long getId();
     User getUser();
     String getName();
     String getDescription();
     String getPicture();
     int getStatus();
     Date getUpdateTime();
     Date getCreateTime();
     int getDiaryCount();
}
