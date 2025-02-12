package it.cascella.friendstimer.service;



import it.cascella.friendstimer.dto.TimerDto;
import it.cascella.friendstimer.dto.UserTimerProgressDto;
import it.cascella.friendstimer.entities.Timer;
import it.cascella.friendstimer.entities.TimerUser;
import it.cascella.friendstimer.repository.TimerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private TimerUserRepository timerUserRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(TimerUserRepository timerUserRepository) {
        this.timerUserRepository = timerUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TimerUser user = timerUserRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("user"))
        );
    }

    public List<TimerDto> getUserTimers(String username) {
        return timerUserRepository.getUserTimers(username);
    }

    public String addTimer(String username, TimerDto timerDto) {
        return "metod not implemented yet";
    }

    public List<UserTimerProgressDto> getUserTimersProgressMap(String username) {
        return timerUserRepository.getUserTimersProgressMap(username);
    }
    public String register(String username, String password) {
        if (timerUserRepository.findByName(username).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        TimerUser user = new TimerUser();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        timerUserRepository.save(user);
        return user.getId().toString();
    }

    public String updateProgress(String username, Long timerId, Time progress) {
        timerUserRepository.updateProgress(username, timerId, progress);
        return "OK";
    }

    public ResponseEntity<String> resetPassword(String email, String newPassword) {
        String encode = passwordEncoder.encode(newPassword);
        try {
            timerUserRepository.updateUserPasswordByMail(email, encode);
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Error updating password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
