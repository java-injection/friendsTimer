package it.cascella.friendstimer.controllers;


import it.cascella.friendstimer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
