package org.example.conferenceservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConferenceRequestDTO {
    private String titre;
    // use String to avoid tight coupling with entity enum
    private String type;
    private LocalDateTime date;
    private Integer durationMinutes;
    private Integer nbInscrits;
}
