package org.bytecub.WedahamineBackend.mappers.master;

import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WHMUserMapper {

    @Mappings({
            @Mapping(target = "statusId", source = "whrStatus.statusId"),
    })
    WHMUserDto toDto(WHMUser user);

    @Mappings({
            @Mapping(source = "statusId", target = "whrStatus.statusId"),
    })
    WHMUser toEntity(WHMUserDto userDto);

    @Mappings({
            @Mapping(target = "statusId", source = "whrStatus.statusId"),
    })
    List<WHMUserDto> listToDto(List<WHMUser> users);
}
