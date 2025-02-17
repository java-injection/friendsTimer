package it.cascella.friendstimer.service;

import it.cascella.friendstimer.repository.TimerUserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.Duration;
import java.util.UUID;


@Slf4j
@Service
public class EmailService {

    private final  JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String FROM ;
    private final TimerUserRepository timerUserRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final static String URL = "http://localhost:8080";

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TimerUserRepository timerUserRepository,StringRedisTemplate stringRedisTemplate) {
        this.javaMailSender = javaMailSender;
        this.timerUserRepository = timerUserRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            log.info("Sending email to: " + to);
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(String mail) {
        if (!timerUserRepository.existsTimerUsersByEmail(mail)) {
            return;
        }
        String token = UUID.randomUUID().toString();
        Duration ttl = Duration.ofMinutes(20);
        stringRedisTemplate.opsForValue().set(token, mail,ttl);
        sendEmail(mail, "Password reset", "Click on the following link to reset your password: "+URL+"/resetpassword?token=" + token);
    }
}
