package it.cascella.friendstimer.controllers;


import it.cascella.friendstimer.dto.TimerDto;
import it.cascella.friendstimer.dto.TimerUserDto;
import it.cascella.friendstimer.dto.UserTimerProgressDto;
import it.cascella.friendstimer.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/mytimers/{username}")
    public ResponseEntity<String> myTimers(@PathVariable String username){
        if (!username.equals(whoAmI())){
            return new ResponseEntity<>("You can't see other people's timers", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getUserTimers(username).toString(), HttpStatus.OK);
    }
    @PostMapping("/addtimer/{username}")
    public ResponseEntity<String> addTimer(@PathVariable String username,@RequestBody TimerDto timerDto){
        System.out.println("Richiesta ricevuta da: " + whoAmI());
        System.out.println("Path username: " + username);
        System.out.println("Timer DTO: " + timerDto);
        if (!username.equals(whoAmI())){
            return new ResponseEntity<>("You can't add timers to other people", HttpStatus.FORBIDDEN);
        }
        if (timerDto==null){
            return new ResponseEntity<>("Timer can't be null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.addTimer(username,timerDto).toString(), HttpStatus.OK);
    }

    @GetMapping("/mytim/{username}")
    private ResponseEntity<List<UserTimerProgressDto>> getTimerUserProgress(@PathVariable String username){

        System.out.println("Richiesta ricevuta da: " + whoAmI());
        List<UserTimerProgressDto> response = userService.getUserTimersProgressMap(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private String whoAmI(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody  TimerUserDto user){
        try{
            return new ResponseEntity<>(userService.register(user.name(),user.password()).toString(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/debug/auth")
    public ResponseEntity<String> debugAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>(authentication.getName() + " | " + authentication.getAuthorities(), HttpStatus.OK);
    }


}
