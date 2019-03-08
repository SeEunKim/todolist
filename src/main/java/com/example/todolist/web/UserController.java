package com.example.todolist.web;

import com.example.todolist.web.model.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String add(User user) {
        logger.debug("## add user : {}", user);
        userService.add(user);
        return "redirect:/";
    }
}

