package com.leo.ourproperty.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
public class JwtUserDetails extends User {
    private com.leo.ourproperty.entity.User user;
    public JwtUserDetails(com.leo.ourproperty.entity.User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId(){
        return this.user.getId();
    }

    public String getRole(){
        return this.user.getRole().name();
    }
}
