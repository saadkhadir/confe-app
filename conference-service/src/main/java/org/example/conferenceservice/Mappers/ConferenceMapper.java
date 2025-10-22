package org.example.conferenceservice.Mappers;

import org.example.conferenceservice.DTO.ConferenceDTO;
import org.example.conferenceservice.DTO.ReviewDTO;
import org.example.conferenceservice.entities.Conference;
import org.example.conferenceservice.entities.Review;
import org.example.conferenceservice.enums.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConferenceMapper {
    private final ReviewMapper reviewMapper;

    public ConferenceMapper(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    public ConferenceDTO toDTO(Conference conference) {
        if (conference == null) return null;
        ConferenceDTO dto = new ConferenceDTO();
        BeanUtils.copyProperties(conference, dto);
        dto.setType(conference.getType() != null ? conference.getType().name() : null);
        if (conference.getReviews() != null) {
            List<ReviewDTO> reviews = conference.getReviews().stream()
                    .map(reviewMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setReviews(reviews);
        }
        return dto;
    }

    public Conference toEntity(ConferenceDTO dto) {
        if (dto == null) return null;
        Conference conference = new Conference();
        BeanUtils.copyProperties(dto, conference);
        if (dto.getType() != null) {
            try {
                conference.setType(Type.valueOf(dto.getType()));
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (dto.getReviews() != null) {
            List<Review> reviews = dto.getReviews().stream()
                    .map(reviewMapper::toEntity)
                    .collect(Collectors.toList());
            reviews.forEach(r -> r.setConference(conference));
            conference.setReviews(reviews);
        }
        return conference;
    }
}
