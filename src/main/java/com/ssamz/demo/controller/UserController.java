package com.ssamz.demo.controller;

import com.ssamz.demo.domain.RoleType;
import com.ssamz.demo.domain.User;
import com.ssamz.demo.dto.ResponseDTO;
import com.ssamz.demo.exception.JBlogException;
import com.ssamz.demo.persistance.UserRepository;
import com.ssamz.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/auth/insertUser")
    public String insertUser()
    {
        return "user/insertUser";
    }

    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(@RequestBody User user)
    {
        User findUser = userService.getUser(user.getUsername());
        System.out.println("Username: " + findUser.getUsername());
        if(findUser.getUsername() == null)
        {
            userService.insertUser(user);
            return new ResponseDTO<>(HttpStatus.OK.value(),
                    user.getUsername() + "님 가입 성공");
        }
        else
        {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),
                    user.getUsername() + "님은 이미 회원이십니다.");
        }

    }
/*    @GetMapping("/user/page")
    public @ResponseBody Page<User> getUserListPaging(

            @PageableDefault(page = 0, size = 2, direction = Sort.Direction.DESC,
                    sort = {"id", "username"}) Pageable pageable) {
        // 첫번째 페이지(0)에 대한 2개의 데이터 조회, id 내림차순 정렬
        return userRepository.findAll(pageable);
    }

    @GetMapping("/user/list")
    public @ResponseBody List<User> getUserList() {
        // 유저 목록 검색
        return userRepository.findAll();
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody String deleteUser(@PathVariable int id) {
        // 유저 삭제
        userRepository.deleteById(id);
        return
                "회원 삭제 성공";
    }


    @Transactional
    @PutMapping("/user")
    public @ResponseBody String updateUser(@RequestBody User user) {
        // 유저 수정
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new JBlogException(user.getId() + "번 회원이 없습니다.");
        });
        findUser.setUsername(user.getUsername());
        findUser.setPassword(user.getPassword());
        findUser.setEmail(user.getEmail());

        return "회원 수정 성공";
    }

    @GetMapping("/user/get/{id}")
    public @ResponseBody User getUser(@PathVariable int id) {
        // 특정 id(회원번호)에 해당하는 User 객체 반환, 검색된 회원이 없을 경우 예외 반환
        User findUser = userRepository.findById(id).orElseThrow(new Supplier<JBlogException>() {

            @Override
            public JBlogException get() {
                return new JBlogException(id + "번 회원이 없습니다.");
            }
        });

        return findUser;
    }

    @PostMapping("/user")
    public @ResponseBody String insertUser(@RequestBody User user) {
        // 유저 추가
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return user.getUsername() + " 회원가입 성공";
    }*/
}
