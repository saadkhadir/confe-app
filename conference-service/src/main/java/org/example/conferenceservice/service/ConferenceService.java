package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ConferenceDTO;
import org.example.conferenceservice.DTO.ReviewDTO;

import java.util.List;

public interface ConferenceService {
    ConferenceDTO create(ConferenceDTO conferenceDTO);
    ConferenceDTO getById(Long id);
    List<ConferenceDTO> getAll();
    ConferenceDTO update(Long id, ConferenceDTO conferenceDTO);
    void delete(Long id);

    ReviewDTO addReview(Long conferenceId, ReviewDTO reviewDTO);
    List<ReviewDTO> getReviews(Long conferenceId);
}
