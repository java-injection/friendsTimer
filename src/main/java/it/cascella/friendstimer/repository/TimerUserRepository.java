package it.cascella.friendstimer.repository;

import it.cascella.friendstimer.entities.TimerUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TimerUserRepository extends CrudRepository<TimerUser, Long> {
    Optional<TimerUser> findByName(String name);

    TimerUser findByNameAndPassword(String name, String password);
}
