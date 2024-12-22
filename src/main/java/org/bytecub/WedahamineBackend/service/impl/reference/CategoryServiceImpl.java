package org.bytecub.WedahamineBackend.service.impl.reference;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.reference.WHRProductCategoryDao;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.error.BadRequestAlertException;
import org.bytecub.WedahamineBackend.mappers.reference.WHRProductCategoryMapper;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.bytecub.WedahamineBackend.service.reference.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final WHRProductCategoryDao categoryDao;
    private final WHRProductCategoryMapper categoryMapper;

    public CategoryServiceImpl(WHRProductCategoryDao categoryDao, WHRProductCategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ResponseEntity<ResponseDto> addProductCategory(WHRProductCategoryDto categoryDto) {
        log.info("Inside CategoryService: addProductCategory");
        try {
            categoryDto.setCategoryId(null);
            if (categoryDto.getCategoryName() == null)
                throw new BadRequestAlertException("Category Name is required", "error", "error");

            categoryDto.setIsActive(true);
            categoryDto = categoryMapper.toDto(categoryDao.save(categoryMapper.toEntity(categoryDto)));

            return new ResponseEntity<>(new ResponseDto(categoryDto.getCategoryId(), "Category added successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: addProductCategory");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<List<WHRProductCategoryDto>>> getPaginatedProductCategories(Integer page, Integer perPage, String search, String sort, String direction) {
        log.info("Inside CategoryService: getPaginatedProductCategories");
        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: getPaginatedProductCategories");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }
}
