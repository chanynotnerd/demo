package com.ssamz.demo.controller;

import com.ssamz.demo.domain.Post;
import com.ssamz.demo.domain.User;
import com.ssamz.demo.dto.ResponseDTO;
import com.ssamz.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

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
    public @ResponseBody ResponseDTO<?> insertPost(@RequestBody Post post,
                                                   HttpSession session)
    {
        // Post 객체를 영속화하기 전 연관된 User 엔티티 설정
        User principal = (User) session.getAttribute("principal");
        post.setUser(principal);
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
