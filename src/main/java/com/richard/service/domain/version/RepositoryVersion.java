package com.richard.service.domain.version;

import com.richard.service.domain.user.User;
import com.richard.service.domain.version.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryVersion extends JpaRepository<Version, Long> {
    Version findTopByOrderByUpdateTimeDesc();
    Version findByVersionName(String name);
}
