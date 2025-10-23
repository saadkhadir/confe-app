package org.example.keynoteservice.DTO;

import lombok.Data;

@Data
public class KeynoteRequestDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}
