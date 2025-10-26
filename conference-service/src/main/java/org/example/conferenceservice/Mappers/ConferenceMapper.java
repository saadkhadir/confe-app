package org.example.conferenceservice.Mappers;

import org.example.conferenceservice.DTO.ConferenceRequestDTO;
import org.example.conferenceservice.DTO.ConferenceResponseDTO;
import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;
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

    public ConferenceResponseDTO toDTO(Conference conference) {
        if (conference == null) return null;
        ConferenceResponseDTO dto = new ConferenceResponseDTO();
        BeanUtils.copyProperties(conference, dto);
        dto.setType(conference.getType() != null ? conference.getType().name() : null);
        if (conference.getReviews() != null) {
            List<ReviewResponseDTO> reviews = conference.getReviews().stream()
                    .map(reviewMapper::toDTO)
                    .collect(Collectors.toList());
            dto.setReviews(reviews);
        }
        return dto;
    }

    public Conference toEntity(ConferenceRequestDTO dto) {
        if (dto == null) return null;
        Conference conference = new Conference();
        BeanUtils.copyProperties(dto, conference);
        if (dto.getType() != null) {
            try {
                conference.setType(Type.valueOf(dto.getType()));
            } catch (IllegalArgumentException ignored) {
            }
        }
        // reviews are not expected in request DTO; nothing to map
        return conference;
    }
}
