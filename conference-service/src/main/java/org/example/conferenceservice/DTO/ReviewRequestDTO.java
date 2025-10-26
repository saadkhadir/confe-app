package org.example.conferenceservice.DTO;

import lombok.Data;

@Data
public class ReviewRequestDTO {
    private String texte;
    private int stars;
    private Long conferenceId;
}

