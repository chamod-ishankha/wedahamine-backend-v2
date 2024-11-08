package org.bytecub.WedahamineBackend.mappers.master;

import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WHMUserMapper {
    WHMUserDto toDto(WHMUser user);
    WHMUser toEntity(WHMUserDto userDto);
    List<WHMUserDto> listToDto(List<WHMUser> users);
}
