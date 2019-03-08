package com.example.todolist.web;

import com.example.todolist.web.exception.UnAuthenticationException;
import com.example.todolist.web.model.User;
import com.example.todolist.web.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void add(User user) {
         userRepository.save(user);
    }

    public User login(String email, String password) throws UnAuthenticationException {
        return userRepository.findByEmail(email)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(UnAuthenticationException::new);
    }
}
