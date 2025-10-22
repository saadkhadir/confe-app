package org.example.conferenceservice.Mappers;

import org.example.conferenceservice.DTO.ReviewDTO;
import org.example.conferenceservice.entities.Conference;
import org.example.conferenceservice.entities.Review;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDTO toDTO(Review review) {
        if (review == null) return null;
        ReviewDTO dto = new ReviewDTO();
        BeanUtils.copyProperties(review, dto);
        if (review.getConference() != null) dto.setConferenceId(review.getConference().getId());
        return dto;
    }

    public Review toEntity(ReviewDTO dto) {
        if (dto == null) return null;
        Review review = new Review();
        BeanUtils.copyProperties(dto, review);
        if (dto.getConferenceId() != null) {
            Conference conf = Conference.builder().id(dto.getConferenceId()).build();
            review.setConference(conf);
        }
        return review;
    }
}
