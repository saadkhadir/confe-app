package org.example.keynoteservice.Mappers;

import org.example.keynoteservice.DTO.KeynoteRequestDTO;
import org.example.keynoteservice.DTO.KeynoteResponseDTO;
import org.example.keynoteservice.entity.Keynote;
import org.springframework.beans.BeanUtils;

public class KeynoteMapper {
    // Utility class - no instantiation
    private KeynoteMapper() {}

    public static KeynoteResponseDTO toResponseDTO(Keynote keynote) {
        if (keynote == null) return null;
        KeynoteResponseDTO dto = new KeynoteResponseDTO();
        BeanUtils.copyProperties(keynote, dto);
        return dto;
    }

    public static Keynote toEntity(KeynoteRequestDTO dto) {
        if (dto == null) return null;
        Keynote keynote = new Keynote();
        BeanUtils.copyProperties(dto, keynote);
        return keynote;
    }

    public static void updateEntityFromRequestDTO(KeynoteRequestDTO dto, Keynote entity) {
        if (dto == null || entity == null) return;
        // copy non-null properties from dto to entity
        BeanUtils.copyProperties(dto, entity);
    }
}
