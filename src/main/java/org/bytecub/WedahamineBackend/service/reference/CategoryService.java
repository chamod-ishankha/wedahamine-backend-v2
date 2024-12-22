package org.bytecub.WedahamineBackend.service.reference;

import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<ResponseDto> addProductCategory(WHRProductCategoryDto categoryDto);

    ResponseEntity<ApiResponseDto<List<WHRProductCategoryDto>>> getPaginatedProductCategories(Integer page, Integer perPage, String search, String sort, String direction);
}
