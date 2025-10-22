package org.example.conferenceservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConferenceDTO {
    private Long id;
    private String titre;
    // use String to avoid tight coupling with entity enum
    private String type;
    private LocalDateTime date;
    private int durationMinutes;
    private int nbInscrits;
    private double score;

    private List<ReviewDTO> reviews;

}
