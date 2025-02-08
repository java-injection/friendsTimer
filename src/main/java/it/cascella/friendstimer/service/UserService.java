package it.cascella.friendstimer.service;



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

import java.util.List;
import java.util.Optional;

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
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("user"))
        );
    }

    public List<Timer> getUserTimers(String username) {
        TimerUser timerUser = timerUserRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        return timerUser.getTimers();

    }
}
