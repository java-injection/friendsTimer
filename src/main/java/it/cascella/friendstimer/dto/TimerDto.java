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

public record TimerDto (String name, Timestamp end) implements Serializable {


    public static TimerDto from(Timer timer) {
        return new TimerDto(timer.getName(), timer.getEnd());
    }
}