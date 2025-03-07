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

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/mytimers")
    public ResponseEntity<List<TimerDto>> myTimers(){
        List<TimerDto> userTimers = userService.getUserTimers(whoAmI());
        return new ResponseEntity<>(userTimers, HttpStatus.OK);
    }

    @PostMapping("/addtimer")
    public ResponseEntity<String> addTimer(@RequestBody TimerDto timerDto){
        log.info("Richiesta ricevuta da: {}", whoAmI());
        log.info("Timer DTO: {}", timerDto);

        if (timerDto==null){
            return new ResponseEntity<>("Timer can't be null", HttpStatus.BAD_REQUEST);
        }
        String s = userService.addTimer(whoAmI(), timerDto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping("/mytim")
    private ResponseEntity<List<UserTimerProgressDto>> getTimerUserProgress(){
        String user = whoAmI();
        log.info("Richiesta ricevuta da: {}", whoAmI());
        List<UserTimerProgressDto> response = userService.getUserTimersProgressMap(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateprogress/{timerId}/{progress}")
    private ResponseEntity<String> updateProgress(@PathVariable Long timerId, @PathVariable String progress){
        String user = whoAmI();
        LocalTime parsedTime = LocalTime.parse(progress);
        Time parsedProgressTime = Time.valueOf(parsedTime);
        System.out.println(parsedProgressTime+" SONO QUI");
        return new ResponseEntity<>(userService.updateProgress(user,timerId,parsedProgressTime), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody TimerUserDto user){
        try{
            return new ResponseEntity<>(userService.register(user.name(),user.password()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<String> home() {
        Optional<Long> userId = userService.getUserId(whoAmI());
        return userId.map(aLong -> new ResponseEntity<>(aLong.toString(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/debug/auth")
    public ResponseEntity<String> debugAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>(authentication.getName() + " | " + authentication.getAuthorities(), HttpStatus.OK);
    }

    private String whoAmI(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }
}
