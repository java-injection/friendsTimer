package it.cascella.friendstimer.repository;

import it.cascella.friendstimer.entities.Timer;
import it.cascella.friendstimer.entities.TimerUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface TimerUserRepository extends CrudRepository<TimerUser, Long> {
    Optional<TimerUser> findByName(String name);

    TimerUser findByNameAndPassword(String name, String password);


    @Query(value = """
select tim.name, expiration_date
from user t
join user_timer on t.id = user_timer.id_user
join timer tim on user_timer.id_timer = tim.id
where t.name = :username;
""", nativeQuery = true)
    List<Objects[]> getUserTimers(@Param("username") String username);

}
