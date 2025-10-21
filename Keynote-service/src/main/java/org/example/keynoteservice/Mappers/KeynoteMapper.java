package org.example.keynoteservice.Mappers;

import org.example.keynoteservice.DTO.KeynoteDTO;
import org.example.keynoteservice.entity.Keynote;
import org.springframework.beans.BeanUtils;

public class KeynoteMapper {
    // Utility class - no instantiation
    private KeynoteMapper() {}

    public static KeynoteDTO toDTO(Keynote keynote) {
        if (keynote == null) return null;
        KeynoteDTO dto = new KeynoteDTO();
        BeanUtils.copyProperties(keynote, dto);
        return dto;
    }

    public static Keynote toEntity(KeynoteDTO dto) {
        if (dto == null) return null;
        Keynote keynote = new Keynote();
        BeanUtils.copyProperties(dto, keynote);
        return keynote;
    }

    public static void updateEntityFromDTO(KeynoteDTO dto, Keynote entity) {
        if (dto == null || entity == null) return;
        // copy non-null properties from dto to entity
        BeanUtils.copyProperties(dto, entity);
    }
}
