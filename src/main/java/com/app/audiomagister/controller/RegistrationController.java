package com.app.audiomagister.controller;

import com.app.audiomagister.domain.User;
import com.app.audiomagister.repos.UserRepo;
import com.app.audiomagister.role.Role;
import com.app.audiomagister.service.SimpleEmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, @RequestParam String password1, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            model.put("message", "User exists!");
            return "registration";
        }
        else if(!user.getPassword().equals(password1)){
            model.put("message", "User exists!");
            return "registration";
        }
        else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "signin";
    }

    @GetMapping("/change")
    public String changePassword(){
        return "changepassword";
    }

    @Autowired
    SimpleEmailService emailService;

    @PostMapping("/change")
    public String changePassword(@RequestParam String email){
        User userFromDb = userRepo.findByUsername(email);
        if(userFromDb != null){
            emailService.sendPasswordEmail(userFromDb);
        }
        return "changepassword";
    }
}
