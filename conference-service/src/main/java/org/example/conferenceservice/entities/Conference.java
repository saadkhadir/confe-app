package org.example.conferenceservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.conferenceservice.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Enumerated(EnumType.STRING)
    private Type type;
    private LocalDateTime date;
    // duration in minutes
    private int durationMinutes;
    private int nbInscrits;
    private double score;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("conference")
    private List<Review> reviews = new ArrayList<>();

}
