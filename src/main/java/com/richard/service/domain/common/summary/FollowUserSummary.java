package com.richard.service.domain.common.summary;

import com.richard.service.domain.user.User;

import java.util.Date;
@Deprecated
public interface FollowUserSummary {
     long id();
     long resourceId();
     int followType();
     long createTime();

     long userId();
     String name();
     String uniqueName();
     String qqOpenId();
     String wxUnionId();
     int age();
     String header();
}
