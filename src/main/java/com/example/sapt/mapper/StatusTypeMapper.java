package com.example.sapt.mapper;

import com.example.sapt.dto.StatusTypeDTO;
import com.example.sapt.entities.StatusType;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeMapper {

    public StatusTypeDTO toDTO(StatusType statusType) {
        return StatusTypeDTO.builder()
                .statusTypeId(statusType.getStatusTypeId())
                .statusName(statusType.getStatusName())
                .createdBy(statusType.getCreatedBy())
                .creationDate(statusType.getCreationDate())

                .build();
    }

    public StatusType toEntity(StatusTypeDTO statusTypeDTO) {
        return StatusType.builder()
                .statusName(statusTypeDTO.getStatusName())
                .createdBy(statusTypeDTO.getCreatedBy())
                .build();
    }
}
