package org.example.keynoteservice.service;

import org.example.keynoteservice.DTO.KeynoteRequestDTO;
import org.example.keynoteservice.DTO.KeynoteResponseDTO;
import org.example.keynoteservice.Mappers.KeynoteMapper;
import org.example.keynoteservice.Repository.KeynoteRepository;
import org.example.keynoteservice.entity.Keynote;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeynoteServiceImpl implements KeynoteService {
    private final KeynoteRepository keynoteRepository;

    public KeynoteServiceImpl(KeynoteRepository repository) {
        this.keynoteRepository = repository;
    }

    @Override
    public KeynoteResponseDTO create(KeynoteRequestDTO keynoteDTO) {
        Keynote entity = KeynoteMapper.toEntity(keynoteDTO);
        Keynote saved = keynoteRepository.save(entity);
        return KeynoteMapper.toResponseDTO(saved);
    }

    @Override
    public KeynoteResponseDTO getById(Long id) {
        return keynoteRepository.findById(id)
                .map(KeynoteMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
    }

    @Override
    public List<KeynoteResponseDTO> getAll() {
        return keynoteRepository.findAll().stream()
                .map(KeynoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KeynoteResponseDTO update(Long id, KeynoteRequestDTO keynoteDTO) {
        Keynote entity = keynoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
        KeynoteMapper.updateEntityFromRequestDTO(keynoteDTO, entity);
        Keynote updated = keynoteRepository.save(entity);
        return KeynoteMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        keynoteRepository.deleteById(id);
    }
}
