package org.example.conferenceservice.RestController;

import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;
import org.example.conferenceservice.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@RequestBody ReviewRequestDTO dto) {
        ReviewResponseDTO created = reviewService.create(dto);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> update(@PathVariable Long id, @RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conference/{conferenceId}")
    public ResponseEntity<List<ReviewResponseDTO>> getByConference(@PathVariable Long conferenceId) {
        return ResponseEntity.ok(reviewService.getByConferenceId(conferenceId));
    }
}
