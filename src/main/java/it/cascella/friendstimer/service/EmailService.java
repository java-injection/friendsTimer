package it.cascella.friendstimer.service;

import it.cascella.friendstimer.repository.TimerUserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;


@Service
public class EmailService {
    private final  JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String FROM ;
    private final TimerUserRepository timerUserRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TimerUserRepository timerUserRepository,StringRedisTemplate stringRedisTemplate) {
        this.javaMailSender = javaMailSender;
        this.timerUserRepository = timerUserRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            System.out.println("Sending email tfrom: " + FROM);
            System.out.println("Sending email to: " + to);
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mail);
            System.out.println("Email sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(String mail) {
        if (!timerUserRepository.existsTimerUsersByMail(mail)) {
            return;
        }
        String token = UUID.randomUUID().toString();
        Duration ttl = Duration.ofMinutes(20);
        stringRedisTemplate.opsForValue().set(mail, token,ttl);
        sendEmail(mail, "Password reset", "Click on the following link to reset your password: http://localhost:8080/resetpassword?token=" + token);
    }
}
