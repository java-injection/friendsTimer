package it.cascella.friendstimer.dto;

import it.cascella.friendstimer.entities.TimerUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link TimerUser}
 */

public record TimerUserDto (

        Long id,
        @Length(min = 8, message = "Password should not be less than 8 chars")
                @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
                        message = "Password should contain at least one digit, one lowercase and one uppercase letter")
        String password,
        @NotBlank(message ="Name Should not be empty or null" )
        @Length(min = 3, max = 20, message = "Name should not be over 20chars or less than 3")
        String name,
        List<TimerDto> timers)
        implements Serializable {

    public static TimerUserDto fromEntity(TimerUser user) {
        List<TimerDto> timers = user.getTimers().stream().map(TimerDto::from).toList();
        return new TimerUserDto(user.getId(), user.getPassword(), user.getUsername(),timers );
    }
}