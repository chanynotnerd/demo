package com.ssamz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KakaoLoginController {
    @GetMapping("/oauth/kakao")
    public @ResponseBody String KakaoCallback(String code)
    {
        return "카카오 서버로부터 받은 CODE 정보: " + code;
    }
}
