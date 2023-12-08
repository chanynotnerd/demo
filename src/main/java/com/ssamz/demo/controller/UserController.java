package com.ssamz.demo.controller;

import com.ssamz.demo.domain.OAuthType;
import com.ssamz.demo.domain.RoleType;
import com.ssamz.demo.domain.User;
import com.ssamz.demo.dto.ResponseDTO;
import com.ssamz.demo.dto.UserDTO;
import com.ssamz.demo.exception.JBlogException;
import com.ssamz.demo.persistance.UserRepository;
import com.ssamz.demo.security.UserDetailsImpl;
import com.ssamz.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${kakao.default.password}")
    private String kakaoPassword;

    @PutMapping("/user")
    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody User user,
                                                   @AuthenticationPrincipal UserDetailsImpl principal)
    {
        // 회원 정보 수정 전, 로그인에 성공한 사용자가 카카오 회원인지 확인
        if(principal.getUser().getOauth().equals(OAuthType.KAKAO))
        {
            // 카카오 회원일 경우 비밀번호 고정
            user.setPassword(kakaoPassword);
        }

        // 회원 정보 수정과 동시에 세션 갱신
        principal.setUser(userService.updateUser(user));
        return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "수정 완료");
    }

    @GetMapping("/user/updateUser")
    public String UpdateUser()
    {
        return "user/updateUser";
    }

    @GetMapping("/auth/login")
    public String login()
    {
        return "system/login";
    }

    @GetMapping("/auth/insertUser")
    public String insertUser()
    {
        return "user/insertUser";
    }

    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(
            @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult)
    {
    /*    // UserDTO 객체에 대한 유효성 검사, AOP로 검사해서 뺀다
		if(bindingResult.hasErrors())
		{
			// 에러가 하나라도 있다면 에러 메세지를 Map에 등록
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors())
			{
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
		}*/
        // UserDTO -> User 객체로 변환
        User user = modelMapper.map(userDTO, User.class);

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
