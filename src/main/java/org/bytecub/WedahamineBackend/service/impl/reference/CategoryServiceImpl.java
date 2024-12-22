package org.bytecub.WedahamineBackend.service.impl.reference;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.reference.WHRProductCategoryDao;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.PaginationDto;
import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.error.BadRequestAlertException;
import org.bytecub.WedahamineBackend.mappers.reference.WHRProductCategoryMapper;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.bytecub.WedahamineBackend.service.reference.CategoryService;
import org.bytecub.WedahamineBackend.utils.Search.FilterCriteria;
import org.bytecub.WedahamineBackend.utils.Search.FilterUtility;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final WHRProductCategoryDao categoryDao;
    private final WHRProductCategoryMapper categoryMapper;
    private final FilterUtility<WHRProductCategory, Long> categoryFilter;

    public CategoryServiceImpl(WHRProductCategoryDao categoryDao, WHRProductCategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.categoryFilter = new FilterUtility<>(categoryDao);
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
            List<FilterCriteria<WHRProductCategory>> filterCriteria = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                filterCriteria.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("categoryName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            // set filter isActive
            filterCriteria.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<WHRProductCategory> paginatedCategories = categoryFilter.filterRecords(page, perPage, sort, direction, filterCriteria);

            ApiResponseDto<List<WHRProductCategoryDto>> response = new ApiResponseDto<>();
            PaginationDto pagination = new PaginationDto();
            pagination.setTotal((int) paginatedCategories.getTotalElements());
            response.setPagination(pagination);
            response.setResult(categoryMapper.listToDto(paginatedCategories.getContent()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: getPaginatedProductCategories");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<WHRProductCategoryDto> getProductCategoryById(Long categoryId) {
        log.info("Inside CategoryService: getProductCategoryById");
        try {
            WHRProductCategory category = categoryDao.findByCategoryIdAndIsActive(categoryId, true)
                    .orElseThrow(() -> new BadRequestAlertException("Category not found", "error", "error"));

            return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: getProductCategoryById");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> updateProductCategory(Long categoryId, WHRProductCategoryDto categoryDto) {
        log.info("Inside CategoryService: updateProductCategory");
        try {
            if (categoryDto.getCategoryName() == null)
                throw new BadRequestAlertException("Category Name is required", "error", "error");
            if (categoryDto.getCategoryId() == null) {
                throw new BadRequestAlertException("Category Id is required", "error", "error");
            }
            if (!categoryDto.getCategoryId().equals(categoryId)) {
                throw new BadRequestAlertException("Category Id is not matching", "error", "error");
            }

            Optional<WHRProductCategory> categoryOp = categoryDao.findByCategoryIdAndIsActive(categoryId, true);
            if (categoryOp.isEmpty())
                throw new BadRequestAlertException("Category not found", "error", "error");

            WHRProductCategory category = categoryOp.get();

            category.setCategoryName(categoryDto.getCategoryName());
            category.setCategoryDescription(categoryDto.getCategoryDescription());

            category = categoryDao.save(category);
            return new ResponseEntity<>(new ResponseDto(category.getCategoryId(), "Category updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: updateProductCategory");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deleteProductCategory(Long categoryId) {
        log.info("Inside CategoryService: deleteProductCategory");
        try {
            Optional<WHRProductCategory> categoryOp = categoryDao.findByCategoryIdAndIsActive(categoryId, true);
            if (categoryOp.isEmpty())
                throw new BadRequestAlertException("Category not found", "error", "error");

            categoryDao.deleteById(categoryId);

            return new ResponseEntity<>(new ResponseDto(categoryId, "Category deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in CategoryService: deleteProductCategory");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }
}
