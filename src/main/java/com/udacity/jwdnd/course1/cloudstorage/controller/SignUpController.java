package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signUpView() {
        return "signup";
    }

    @PostMapping()
    public String signUpUser(@ModelAttribute User user, Model model) {

        boolean userNameAvailable = userService.getUser(user.getUsername()) == null;

        if (userNameAvailable) {
            if (userService.createUser(user) > 0) {
                model.addAttribute("signupSuccess", true);
            }
            else {
                model.addAttribute("signupError", "There was an error signing you up, please try again later.");
            }
        }
        else {
            model.addAttribute("signupError", "Username already exists");
        }

        return "signup";
    }
}
