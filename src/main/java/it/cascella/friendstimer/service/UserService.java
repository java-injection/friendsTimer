package it.cascella.friendstimer.service;



import it.cascella.friendstimer.dto.TimerDto;
import it.cascella.friendstimer.entities.Timer;
import it.cascella.friendstimer.entities.TimerUser;
import it.cascella.friendstimer.repository.TimerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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
        List<Objects[]> userTimers = timerUserRepository.getUserTimers(username);
        return userTimers.stream()
                .map(objects -> new TimerDto(
                        (String) objects[0].toString(),
                        Timestamp.valueOf(objects[0].toString()).toLocalDateTime()
                        ))
                .collect(Collectors.toList());
    }
}
