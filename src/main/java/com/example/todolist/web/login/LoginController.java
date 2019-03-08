package com.example.todolist.web.login;

import com.example.todolist.web.HttpSessionUtils;
import com.example.todolist.web.UserService;
import com.example.todolist.web.exception.UnAuthenticationException;
import com.example.todolist.web.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class LoginController {
    private static final Logger logger = getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/kakao_oauth")
    public String kakaologinGet(@RequestParam("code") String code, HttpSession session) {
        logger.debug("## kakaologinGet : {}",  code);

        KakaoRestApi kakaoRestApi = new KakaoRestApi();
        JsonNode node = kakaoRestApi.getAccessToken(code);
        String token = node.get("access_token").toString();
        session.setAttribute("token", token);
        return "/index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, String email, String password) {
        logger.debug("##login ");
        try {
            User user = userService.login(email, password);
            session.setAttribute(HttpSessionUtils.USER_SESSION_USER, user);
            logger.debug("### loginSuccess : {}", user.toString());
            return "redirect:/";
        } catch (UnAuthenticationException e) {
            logger.debug("### loginError : {}", e.getStackTrace());
            return "/user/login_fail";
        }
    }
}
