package it.cascella.friendstimer.controllers;


import it.cascella.friendstimer.dto.TimerDto;
import it.cascella.friendstimer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/mytimers/{username}")
    public String myTimers(@PathVariable String username){
        if (!username.equals(whoAmI())){
            return "You can't see other people's timers";
        }
        return userService.getUserTimers(username).toString();
    }
    @PostMapping("/addtimer/{username}")
    public String addTimer(@PathVariable String username,@RequestBody TimerDto timerDto){
        System.out.println("Richiesta ricevuta da: " + whoAmI());
        System.out.println("Path username: " + username);
        System.out.println("Timer DTO: " + timerDto);
        if (!username.equals(whoAmI())){
            return "You can't add timers for other people";
        }
        if (timerDto==null){
            return "You can't add a null timer";
        }
        return userService.addTimer(username,timerDto).toString();
    }

    @GetMapping("/whoami")
    private String whoAmI(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }

    @GetMapping("/debug/auth")
    public String debugAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Authenticated user: " + authentication.getName() + " | Roles: " + authentication.getAuthorities();
    }


}
