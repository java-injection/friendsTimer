package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resetpassword")
public class ResetPasswordController {
    private final StringRedisTemplate stringRedisTemplate;
    private final UserService userService;

    @Autowired
    public ResetPasswordController(StringRedisTemplate stringRedisTemplate,UserService userService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.userService=userService;
    }


    @PostMapping("{token}/{newPassword}")
    public void resetPassword(@PathVariable String token, @PathVariable String newPassword) {
        String email = stringRedisTemplate.opsForValue().get(token);
        System.out.println(token+" e la nuova pswd: "+newPassword);
        if (email==null||email.isBlank()){
            System.out.println("nono ho trovato nulla");
            return;
        }
        userService.resetPassword(email,newPassword);
    }
}
