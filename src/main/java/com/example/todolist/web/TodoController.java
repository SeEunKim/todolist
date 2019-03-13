package com.example.todolist.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @GetMapping("/form")
    public String form() {
        return "/todo/todo";
    }
}
