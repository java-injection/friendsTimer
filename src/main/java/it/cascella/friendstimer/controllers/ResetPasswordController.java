package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resetpassword")
public class ResetPasswordController {
    private final StringRedisTemplate stringRedisTemplate;
    private UserService userService;

    @Autowired
    public ResetPasswordController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @PostMapping("{token}/{newPassword}")
    public void resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        String s = stringRedisTemplate.opsForValue().get(token);
        if (s==null||s.isBlank()){
            return;
        }
        userService.resetPassword(s,newPassword);
    }
}
