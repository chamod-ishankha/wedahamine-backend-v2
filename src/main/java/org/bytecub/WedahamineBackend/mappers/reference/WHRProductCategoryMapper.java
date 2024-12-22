package org.bytecub.WedahamineBackend.mappers.reference;

import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WHRProductCategoryMapper {

    WHRProductCategory toEntity(WHRProductCategoryDto WHRProductCategoryDto);

    WHRProductCategoryDto toDto(WHRProductCategory WHRProductCategory);

    List<WHRProductCategoryDto> listToDto(List<WHRProductCategory> WHRProductCategoryList);
}