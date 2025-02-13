package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.dto.PasswordDto;
import it.cascella.friendstimer.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

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

    @PostMapping("")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordDto newPassword) {
        String email = stringRedisTemplate.opsForValue().get(newPassword.token());
        if (email==null||email.isBlank()){
            System.out.println("nono ho trovato nulla");
            return new ResponseEntity<>("Invalid Token", HttpStatus.NOT_FOUND);
        }
        stringRedisTemplate.delete(newPassword.token());
        return userService.resetPassword(email,newPassword.password());
    }
}
