package it.cascella.friendstimer.dto;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record PasswordDto(
        @Length(min = 8, message = "Password should not be less than 8 chars")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
                message = "Password should contain at least one digit, one lowercase and one uppercase letter")
        String password,
        String token
) {

}
