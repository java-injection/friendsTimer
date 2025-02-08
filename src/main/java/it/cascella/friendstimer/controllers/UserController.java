package it.cascella.friendstimer.controllers;


import it.cascella.friendstimer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("user/")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("login/{name}/{password}")
    public String login(@PathVariable String name, @PathVariable String password){
        return userService.login(name, password);
    }

    @PostMapping("register/{name}/{password}")
    public String register(@PathVariable String name, @PathVariable String password){
        return userService.register(name, password);
    }
}
