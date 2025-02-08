package it.cascella.friendstimer.dto;

import it.cascella.friendstimer.entities.TimerUser;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link TimerUser}
 */

public record TimerUserDto (Long id,String password, String name, List<TimerDto> timers) implements Serializable {

    public static TimerUserDto fromEntity(TimerUser user) {
        List<TimerDto> timers = user.getTimers().stream().map(TimerDto::from).toList();
        return new TimerUserDto(user.getId(), user.getPassword(), user.getUsername(),timers );
    }
}