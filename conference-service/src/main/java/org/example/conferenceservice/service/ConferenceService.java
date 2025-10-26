package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ConferenceRequestDTO;
import org.example.conferenceservice.DTO.ConferenceResponseDTO;
import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;

import java.util.List;

public interface ConferenceService {
    ConferenceResponseDTO create(ConferenceRequestDTO conferenceDTO);
    ConferenceResponseDTO getById(Long id);
    List<ConferenceResponseDTO> getAll();
    ConferenceResponseDTO update(Long id, ConferenceRequestDTO conferenceDTO);
    void delete(Long id);

    ReviewResponseDTO addReview(Long conferenceId, ReviewRequestDTO reviewDTO);
    List<ReviewResponseDTO> getReviews(Long conferenceId);
}
