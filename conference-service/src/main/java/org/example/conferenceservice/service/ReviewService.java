package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO create(ReviewRequestDTO reviewDTO);
    ReviewResponseDTO getById(Long id);
    List<ReviewResponseDTO> getAll();
    ReviewResponseDTO update(Long id, ReviewRequestDTO reviewDTO);
    void delete(Long id);
    List<ReviewResponseDTO> getByConferenceId(Long conferenceId);
}
