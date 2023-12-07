package com.ssamz.demo.service;

import com.ssamz.demo.domain.RoleType;
import com.ssamz.demo.domain.User;
import com.ssamz.demo.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUser(String username)
    {
        // 검색결과가 없을 때 빈 User 객체로 반환
        User findUser = userRepository.findByUsername(username).orElseGet(
                new Supplier<User>() {
                    @Override
                    public User get() {
                        return new User();
                    }
                });
        return findUser;
    }
    @Transactional
    public void insertUser(User user) {
        // 비밀번호를 암호화하여 설정한다.
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(RoleType.USER);
        /*if(user.getOauth() == null)
        {
            user.setOauth(OAuthType.JBLOG);
        }*/
        userRepository.save(user);
    }
}
