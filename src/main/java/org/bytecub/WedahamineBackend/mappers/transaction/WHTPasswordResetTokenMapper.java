package org.bytecub.WedahamineBackend.mappers.transaction;

import org.bytecub.WedahamineBackend.dto.transaction.WHTPasswordResetTokenDto;
import org.bytecub.WedahamineBackend.mappers.master.WHMUserMapper;
import org.bytecub.WedahamineBackend.model.transaction.WHTPasswordResetToken;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {WHMUserMapper.class})
public interface WHTPasswordResetTokenMapper {
    @Mappings({
            @Mapping(target = "whmUser.userId", source = "userId"),
    })
    WHTPasswordResetToken toEntity(WHTPasswordResetTokenDto WHTPasswordResetTokenDto);

    @Mappings({
            @Mapping(source = "whmUser.userId", target = "userId"),
    })
    WHTPasswordResetTokenDto toDto(WHTPasswordResetToken WHTPasswordResetToken);
}