package org.bytecub.WedahamineBackend.mappers.master;

import org.bytecub.WedahamineBackend.dto.master.WHMProductDto;
import org.bytecub.WedahamineBackend.model.master.WHMProduct;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WHMProductMapper {

    @Mappings({
            @Mapping(target = "whrProductCategory.categoryId", source = "categoryId"),
            @Mapping(target = "whrStatus.statusId", source = "statusId"),
    })
    WHMProduct toEntity(WHMProductDto WHMProductDto);

    @Mappings({
            @Mapping(source = "whrProductCategory.categoryId", target = "categoryId"),
            @Mapping(source = "whrStatus.statusId", target = "statusId"),
    })
    WHMProductDto toDto(WHMProduct WHMProduct);

    @Mappings({
            @Mapping(source = "whrProductCategory.categoryId", target = "categoryId"),
            @Mapping(source = "whrStatus.statusId", target = "statusId"),
    })
    List<WHMProductDto> listToDto(List<WHMProduct> WHMProduct);
}