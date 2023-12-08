package com.ssamz.demo.security;

import com.ssamz.demo.domain.User;
import com.ssamz.demo.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User principal = userRepository.findByUsername(username).orElseThrow(()->{
            return new UsernameNotFoundException(username + "사용자가 없습니다.");
        });
        return new UserDetailsImpl(principal);
    }
}
