package it.cascella.friendstimer.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;

public record UserTimerProgressDto (
        Integer timerId,
        Time progress
){
}
