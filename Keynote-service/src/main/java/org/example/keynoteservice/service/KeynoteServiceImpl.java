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
    private final KeynoteRepository repository;

    public KeynoteServiceImpl(KeynoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public KeynoteResponseDTO create(KeynoteRequestDTO keynoteDTO) {
        Keynote entity = KeynoteMapper.toEntity(keynoteDTO);
        Keynote saved = repository.save(entity);
        return KeynoteMapper.toResponseDTO(saved);
    }

    @Override
    public KeynoteResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(KeynoteMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
    }

    @Override
    public List<KeynoteResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(KeynoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KeynoteResponseDTO update(Long id, KeynoteRequestDTO keynoteDTO) {
        Keynote entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
        KeynoteMapper.updateEntityFromRequestDTO(keynoteDTO, entity);
        Keynote updated = repository.save(entity);
        return KeynoteMapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
