package org.example.conferenceservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
    private Long id;
    private LocalDateTime date;
    private String texte;
    private int stars;
    private Long conferenceId;
}

