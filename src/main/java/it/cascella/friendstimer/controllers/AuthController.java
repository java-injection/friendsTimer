package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private EmailService emailService;


    @Autowired
    public AuthController( EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping("test/{mail}")
    public void sendEmail(@PathVariable  String mail){
        emailService.sendEmail(mail, "Test", "Test");
    }


   /* @GetMapping("/resetpassword/{mail}")
    public ResponseEntity<String> resetPassword(String mail){
        authService.resetPassword(mail);
        return new ResponseEntity<>("if Email exists a reset password email has been sent to that address", org.springframework.http.HttpStatus.OK);
    }*/
}
