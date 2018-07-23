package com.richard.service.service;

import com.richard.service.domain.common.summary.FollowDiarySummary;
import com.richard.service.domain.common.summary.FollowUserSummary;
import com.richard.service.domain.diary.*;
import com.richard.service.domain.user.Follow;
import com.richard.service.domain.user.RepositoryFollow;
import com.richard.service.domain.user.RepositoryUser;
import com.richard.service.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * by Richard on 2017/9/10
 * desc:
 */
@Service
public class UserService {
    @Autowired
    private RepositoryUser repositoryUser;
    @Autowired
    private RepositoryFollow repositoryFollow;

    public boolean addFollow(User user, long resourceId, int followType) {
        Follow follow = repositoryFollow.findByResourceIdAndUser(resourceId, user);
        if(follow == null){
            follow = new Follow(user, resourceId, followType);
            repositoryFollow.save(follow);
            return true;
        }else {
            repositoryFollow.delete(follow);
            return false;
        }
    }

    public List<Map> getFollowUsers(User user, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryFollow.getFollowUsers(user.getId(), pageable);
        return page.getContent();
    }

    public List<Map> getCollectDiary(User user, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page page = repositoryFollow.getCollectDiaries(user.getId(), pageable);
        return page.getContent();
    }
}
