package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO create(ReviewDTO reviewDTO);
    ReviewDTO getById(Long id);
    List<ReviewDTO> getAll();
    ReviewDTO update(Long id, ReviewDTO reviewDTO);
    void delete(Long id);
    List<ReviewDTO> getByConferenceId(Long conferenceId);
}
