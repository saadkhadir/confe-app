package org.example.keynoteservice.RestController;

import org.example.keynoteservice.DTO.KeynoteDTO;
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
    public ResponseEntity<KeynoteDTO> create(@RequestBody KeynoteDTO dto) {
        KeynoteDTO created = keynoteService.create(dto);
        return ResponseEntity.created(URI.create("/api/keynotes/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<KeynoteDTO>> getAll() {
        return ResponseEntity.ok(keynoteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeynoteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(keynoteService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeynoteDTO> update(@PathVariable Long id, @RequestBody KeynoteDTO dto) {
        return ResponseEntity.ok(keynoteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        keynoteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
