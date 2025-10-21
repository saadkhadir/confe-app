package org.example.keynoteservice.service;

import org.example.keynoteservice.DTO.KeynoteDTO;
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
    public KeynoteDTO create(KeynoteDTO keynoteDTO) {
        Keynote entity = KeynoteMapper.toEntity(keynoteDTO);
        Keynote saved = repository.save(entity);
        return KeynoteMapper.toDTO(saved);
    }

    @Override
    public KeynoteDTO getById(Long id) {
        return repository.findById(id)
                .map(KeynoteMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
    }

    @Override
    public List<KeynoteDTO> getAll() {
        return repository.findAll().stream()
                .map(KeynoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KeynoteDTO update(Long id, KeynoteDTO keynoteDTO) {
        Keynote entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote not found: " + id));
        KeynoteMapper.updateEntityFromDTO(keynoteDTO, entity);
        Keynote updated = repository.save(entity);
        return KeynoteMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
