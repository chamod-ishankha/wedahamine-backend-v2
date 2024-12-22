package org.bytecub.WedahamineBackend.rest.reference;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.bytecub.WedahamineBackend.service.reference.ReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/reference")
@Slf4j
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    /**
     * Category Apis
     */

    @PostMapping("/category")
    public ResponseEntity<ResponseDto> addProductCategory(@RequestBody WHRProductCategoryDto categoryDto) {
        log.info("Inside ReferenceController: addProductCategory");
        return referenceService.getCategoryService().addProductCategory(categoryDto);
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponseDto<List<WHRProductCategoryDto>>> getPaginatedProductCategories(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer per_page,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "categoryId", required = false) String sort,
            @RequestParam(defaultValue = "asc", required = false) String direction
    ) {
        log.info("Inside ReferenceController: getPaginatedProductCategories");
        return referenceService.getCategoryService().getPaginatedProductCategories(page, per_page, search, sort, direction);
    }


}
