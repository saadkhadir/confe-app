package org.example.keynoteservice.RestController;

import org.example.keynoteservice.DTO.KeynoteRequestDTO;
import org.example.keynoteservice.DTO.KeynoteResponseDTO;
import org.example.keynoteservice.service.KeynoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
public class KeynoteController {

    private final KeynoteService keynoteService;

    public KeynoteController(KeynoteService keynoteService) {
        this.keynoteService = keynoteService;
    }

    @PostMapping
    public ResponseEntity<KeynoteResponseDTO> create(@RequestBody KeynoteRequestDTO dto) {
        KeynoteResponseDTO created = keynoteService.create(dto);
        return ResponseEntity.created(URI.create("/api/keynotes/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<KeynoteResponseDTO>> getAll() {
        return ResponseEntity.ok(keynoteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeynoteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(keynoteService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeynoteResponseDTO> update(@PathVariable Long id, @RequestBody KeynoteRequestDTO dto) {
        return ResponseEntity.ok(keynoteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        keynoteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
