package com.ssamz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

	@GetMapping("/post/insertPost")
	public String insertPost()
	{
		return "post/insertPost";
	}
    @GetMapping({"", "/"})
	public String getPostList()
	{
		return "index";
	}
}
