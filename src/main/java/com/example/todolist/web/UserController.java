package com.example.todolist.web;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class UserController {
    private static final Logger logger = getLogger(UserController.class);

    @GetMapping("/users/create")
    public String createForm() {
        return "/user/form";
    }

    @GetMapping("/users/login")
    public String loginForm() {
        
        return "/user/login";
    }

    @GetMapping("/users/oauth")
    public String oauth(String code) {
        logger.debug("## oauth : {}",  code);
        return "";
    }

    @GetMapping("/kakao_oauth")
    public String kakaologinGet(@RequestParam("code") String code, HttpSession session) {
        logger.debug("## kakaologinGet : {}",  code);

        KakaoRestApi kakaoRestApi = new KakaoRestApi();
        JsonNode node = kakaoRestApi.getAccessToken(code);
        System.out.println(node);
        String token = node.get("access_token").toString();
        session.setAttribute("token", token);

        return "/index";
    }
}
