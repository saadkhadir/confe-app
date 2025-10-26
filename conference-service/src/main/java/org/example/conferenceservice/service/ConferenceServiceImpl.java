package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ConferenceRequestDTO;
import org.example.conferenceservice.DTO.ConferenceResponseDTO;
import org.example.conferenceservice.DTO.ReviewRequestDTO;
import org.example.conferenceservice.DTO.ReviewResponseDTO;
import org.example.conferenceservice.Mappers.ConferenceMapper;
import org.example.conferenceservice.Mappers.ReviewMapper;
import org.example.conferenceservice.Repository.ConferenceRepository;
import org.example.conferenceservice.Repository.ReviewRepository;
import org.example.conferenceservice.entities.Conference;
import org.example.conferenceservice.entities.Review;
import org.example.conferenceservice.enums.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final ReviewRepository reviewRepository;
    private final ConferenceMapper conferenceMapper;
    private final ReviewMapper reviewMapper;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ReviewRepository reviewRepository,
                                 ConferenceMapper conferenceMapper, ReviewMapper reviewMapper) {
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
        this.conferenceMapper = conferenceMapper;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ConferenceResponseDTO create(ConferenceRequestDTO conferenceDTO) {
        Conference entity = conferenceMapper.toEntity(conferenceDTO);
        Conference saved = conferenceRepository.save(entity);
        return conferenceMapper.toDTO(saved);
    }

    @Override
    public ConferenceResponseDTO getById(Long id) {
        Conference c = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found: " + id));
        return conferenceMapper.toDTO(c);
    }

    @Override
    public List<ConferenceResponseDTO> getAll() {
        return conferenceRepository.findAll().stream()
                .map(conferenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConferenceResponseDTO update(Long id, ConferenceRequestDTO conferenceDTO) {
        Conference entity = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference not found: " + id));
        // copy non-null properties from DTO to entity
        String[] ignore = getNullPropertyNames(conferenceDTO);
        BeanUtils.copyProperties(conferenceDTO, entity, ignore);
        // handle type string -> enum
        if (conferenceDTO.getType() != null) {
            try {
                entity.setType(Type.valueOf(conferenceDTO.getType()));
            } catch (IllegalArgumentException ignoredEx) {
                // ignore invalid type
            }
        }
        Conference updated = conferenceRepository.save(entity);
        return conferenceMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        conferenceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ReviewResponseDTO addReview(Long conferenceId, ReviewRequestDTO reviewDTO) {
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conference not found: " + conferenceId));

        Review review = reviewMapper.toEntity(reviewDTO);
        if (review.getDate() == null) review.setDate(LocalDateTime.now());
        // ensure the review references the managed conference
        review.setConference(conference);

        Review saved = reviewRepository.save(review);

        // recalculate average score
        List<Review> reviews = reviewRepository.findByConferenceId(conferenceId);
        double avg = reviews.stream().mapToInt(Review::getStars).average().orElse(0.0);
        conference.setScore(avg);
        conferenceRepository.save(conference);

        return reviewMapper.toDTO(saved);
    }

    @Override
    public List<ReviewResponseDTO> getReviews(Long conferenceId) {
        return reviewRepository.findByConferenceId(conferenceId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    // helper to return names of properties that are null in the source bean
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
