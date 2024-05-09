package com.leo.ourproperty.jwt;

import com.leo.ourproperty.entity.User;
import com.leo.ourproperty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtToken getTokenAuthenticate(String email){
        User.Role role = userService.findRoleByEmail(email);
        return JwtUtils.createToken(email, role.name());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        return new JwtUserDetails(user);
    }
}
