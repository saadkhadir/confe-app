package org.example.keynoteservice.service;

import org.example.keynoteservice.DTO.KeynoteDTO;

import java.util.List;

public interface KeynoteService {
    KeynoteDTO create(KeynoteDTO keynoteDTO);
    KeynoteDTO getById(Long id);
    List<KeynoteDTO> getAll();
    KeynoteDTO update(Long id, KeynoteDTO keynoteDTO);
    void delete(Long id);
}
