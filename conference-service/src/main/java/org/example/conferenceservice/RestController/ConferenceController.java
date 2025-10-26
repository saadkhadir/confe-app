package org.example.conferenceservice.RestController;

import org.example.conferenceservice.DTO.ConferenceRequestDTO;
import org.example.conferenceservice.DTO.ConferenceResponseDTO;
import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;
import org.example.conferenceservice.service.ConferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping
    public ResponseEntity<ConferenceResponseDTO> create(@RequestBody ConferenceRequestDTO dto) {
        ConferenceResponseDTO created = conferenceService.create(dto);
        return ResponseEntity.created(URI.create("/api/conferences/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceResponseDTO>> getAll() {
        return ResponseEntity.ok(conferenceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConferenceResponseDTO> update(@PathVariable Long id, @RequestBody ConferenceRequestDTO dto) {
        return ResponseEntity.ok(conferenceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long id, @RequestBody ReviewRequestDTO reviewDTO) {
        ReviewResponseDTO created = conferenceService.addReview(id, reviewDTO);
        return ResponseEntity.created(URI.create("/api/conferences/" + id + "/reviews/" + created.getId())).body(created);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(@PathVariable Long id) {
        return ResponseEntity.ok(conferenceService.getReviews(id));
    }
}
