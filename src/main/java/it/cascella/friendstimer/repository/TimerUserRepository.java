package it.cascella.friendstimer.repository;

import it.cascella.friendstimer.dto.TimerDto;
import it.cascella.friendstimer.dto.UserTimerProgressDto;
import it.cascella.friendstimer.entities.Timer;
import it.cascella.friendstimer.entities.TimerUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface TimerUserRepository extends CrudRepository<TimerUser, Long> {
    Optional<TimerUser> findByName(String name);

    Boolean existsTimerUsersByEmail(String mail);

    TimerUser findByNameAndPassword(String name, String password);



    @Transactional
    @Query(value = """
select tim.id, tim.name, tim.expiration_date
from user t
join user_timer on t.id = user_timer.id_user
join timer tim on user_timer.id_timer = tim.id
where t.name = :username;
""", nativeQuery = true)
    List<TimerDto> getUserTimers(@Param("username") String username);


    @Transactional
    @Query(value = """
select tim.id_timer, tim.progress
from user t
join user_timer tim on t.id = tim.id_user
where t.name = :username;
""", nativeQuery = true)
    List<UserTimerProgressDto> getUserTimersProgressMap(@Param("username") String username);


    @Modifying
    @Transactional
    @Query(value = """
UPDATE user_timer t
SET t.progress = ADDTIME(progress, :progress)
WHERE id_timer = :timerId AND id_user = (select id from user where name=:username);
""", nativeQuery = true)
    void updateProgress(String username, Long timerId, Time progress);

    Optional<Object> findByEmail(String mail);

    @Query("update TimerUser t set t.password = :password where t.email = :email")
    @Modifying
    @Transactional
    void updateUserPasswordByMail(@Email String email,String password);
}
