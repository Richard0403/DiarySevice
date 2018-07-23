package com.richard.service.security.auth;


import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.user.User;

import java.util.Map;

public interface AuthService {
    RestResult register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
