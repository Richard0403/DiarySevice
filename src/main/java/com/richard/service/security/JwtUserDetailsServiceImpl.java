package com.richard.service.security;

import com.richard.service.domain.user.RepositoryUser;
import com.richard.service.domain.user.User;
import com.richard.service.security.demain.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: Richard on
 * 2017/12/5
 * desc:
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryUser.findByUniqueName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with qqOpenId '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
