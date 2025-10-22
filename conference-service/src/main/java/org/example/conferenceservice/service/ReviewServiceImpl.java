package org.example.conferenceservice.service;

import org.example.conferenceservice.DTO.ReviewDTO;
import org.example.conferenceservice.Mappers.ReviewMapper;
import org.example.conferenceservice.Repository.ReviewRepository;
import org.example.conferenceservice.entities.Conference;
import org.example.conferenceservice.entities.Review;
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
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewDTO create(ReviewDTO reviewDTO) {
        Review review = reviewMapper.toEntity(reviewDTO);
        if (review.getDate() == null) review.setDate(LocalDateTime.now());
        Review saved = reviewRepository.save(review);
        return reviewMapper.toDTO(saved);
    }

    @Override
    public ReviewDTO getById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));
    }

    @Override
    public List<ReviewDTO> getAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO update(Long id, ReviewDTO reviewDTO) {
        Review entity = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));
        // copy non-null properties from DTO to entity
        String[] ignore = getNullPropertyNames(reviewDTO);
        BeanUtils.copyProperties(reviewDTO, entity, ignore);
        // handle conferenceId mapping if present
        if (reviewDTO.getConferenceId() != null) {
            entity.setConference(Conference.builder().id(reviewDTO.getConferenceId()).build());
        }
        Review updated = reviewRepository.save(entity);
        return reviewMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDTO> getByConferenceId(Long conferenceId) {
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
