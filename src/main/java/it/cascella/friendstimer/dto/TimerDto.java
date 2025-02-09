package it.cascella.friendstimer.dto;

import it.cascella.friendstimer.entities.Timer;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Timer}
 */

public record TimerDto (long id,String name, Timestamp end) implements Serializable {


    public static TimerDto from(Timer timer) {
        return new TimerDto(timer.getId(),timer.getName(), timer.getEnd());
    }
}