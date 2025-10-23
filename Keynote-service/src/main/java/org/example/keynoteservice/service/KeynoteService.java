package org.example.keynoteservice.service;

import org.example.keynoteservice.DTO.KeynoteRequestDTO;
import org.example.keynoteservice.DTO.KeynoteResponseDTO;

import java.util.List;

public interface KeynoteService {
    KeynoteResponseDTO create(KeynoteRequestDTO keynoteDTO);
    KeynoteResponseDTO getById(Long id);
    List<KeynoteResponseDTO> getAll();
    KeynoteResponseDTO update(Long id, KeynoteRequestDTO keynoteDTO);
    void delete(Long id);
}
