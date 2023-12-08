package com.ssamz.demo.controller;

import com.ssamz.demo.domain.Post;
import com.ssamz.demo.domain.User;
import com.ssamz.demo.dto.PostDTO;
import com.ssamz.demo.dto.ResponseDTO;
import com.ssamz.demo.security.UserDetailsImpl;
import com.ssamz.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @DeleteMapping("/post/{id}")
    public @ResponseBody ResponseDTO<?> deletePost(@PathVariable int id)
    {
        postService.deletePost(id);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                id + "번 포스트를 삭제하였습니다.");
    }

    @PutMapping("/post")
    public @ResponseBody ResponseDTO<?> updatePost(@RequestBody Post post)
    {
        postService.updatePost(post);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                post.getId() + "번 포스트를 수정했습니다.");
    }

    @GetMapping("/post/updatePost/{id}")
    public String updatePost(@PathVariable int id, Model model)
    {
        model.addAttribute("post", postService.getPost(id));
        return "post/updatePost";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable int id, Model model)
    {
        model.addAttribute("post", postService.getPost(id));
        return "post/getPost";
    }

    @PostMapping("/post")
    public @ResponseBody ResponseDTO<?> insertPost(
            @Valid @RequestBody PostDTO postDTO, BindingResult bindingResult,
            HttpSession session, @AuthenticationPrincipal UserDetailsImpl principal)
    {
        /*// PostDTO 객체에 대한 유효성 검사, AOP로 유효성 검사해서 뺀다.
		if(bindingResult.hasErrors())
		{
			// 에러가 하나라도 있다면 에러 메세지를 Map에 등록
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors())
			{
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
			}*/
        // PostDTO -> Post 객체로 변환
        Post post = modelMapper.map(postDTO, Post.class);
        // Post 객체를 영속화하기 전 연관된 User 엔티티 설정
        post.setUser(principal.getUser());
        post.setCnt(0);

        postService.insertPost(post);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                "새로운 포스트를 등록했습니다.");
    }

    @GetMapping("/post/insertPost")
    public String insertPost() {
        return "post/insertPost";
    }

    @GetMapping({"", "/"})
    public String getPostList(Model model, @PageableDefault(size = 3, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable)
    {
        model.addAttribute("postList", postService.getPostList(pageable));
        return "index";
    }
}
