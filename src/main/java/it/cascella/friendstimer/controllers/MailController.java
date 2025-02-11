package it.cascella.friendstimer.controllers;

import it.cascella.friendstimer.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {

    private final EmailService emailService;

    @Autowired
    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/resetpassword")
    public void resetPassword(@RequestBody String mail) {
        log.debug("Reset password request for mail: " + mail);
        emailService.resetPassword(mail);
    }
}
