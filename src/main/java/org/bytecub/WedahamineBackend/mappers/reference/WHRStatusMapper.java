package org.bytecub.WedahamineBackend.mappers.reference;

import org.bytecub.WedahamineBackend.dto.reference.WHRStatusDto;
import org.bytecub.WedahamineBackend.model.reference.WHRStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WHRStatusMapper {

    WHRStatusDto toDto(WHRStatus status);

    WHRStatus toEntity(WHRStatusDto status);

    List<WHRStatusDto> listToDto(List<WHRStatus> whrStatusList);

}
