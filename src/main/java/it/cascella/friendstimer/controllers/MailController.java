package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final EmailService emailService;

    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/resetpassword")
    public void resetPassword(@RequestParam String mail) {
        System.out.println("Reset password request for mail: " + mail);
        emailService.resetPassword(mail);
    }
}
