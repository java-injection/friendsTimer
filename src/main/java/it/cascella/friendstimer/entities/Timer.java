package it.cascella.friendstimer.entities;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "timer")
@Data
@Getter
@Setter
@AllArgsConstructor
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp end;
    private String name;

    public long getId() {
        return id;
    }

    public Timestamp getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }
}
