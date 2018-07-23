package com.richard.service.domain.diary;

import com.richard.service.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * by Richard on 2017/9/10
 * desc:
 */
public interface RepositoryPraise extends JpaRepository<Praise,Long> {
    Praise findFirstByResourceIdAndUser(long resourceId, User user);
}

