package com.infoshareacademy.responsibledrinkersweb.config.userlogging;

import com.infoshareacademy.responsibledrinkersweb.dto.UserDto;
import com.infoshareacademy.responsibledrinkersweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.findByUserName(username);
        return new UserPrincipal(userDto);
    }
}
