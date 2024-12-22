package org.bytecub.WedahamineBackend.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.master.WHMProductDao;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.master.WHMProductDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.PaginationDto;
import org.bytecub.WedahamineBackend.error.BadRequestAlertException;
import org.bytecub.WedahamineBackend.mappers.master.WHMProductMapper;
import org.bytecub.WedahamineBackend.model.master.WHMProduct;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.bytecub.WedahamineBackend.service.master.ProductService;
import org.bytecub.WedahamineBackend.utils.Search.FilterCriteria;
import org.bytecub.WedahamineBackend.utils.Search.FilterUtility;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Fidelity;
import java.util.ArrayList;
import java.util.List;

import static org.bytecub.WedahamineBackend.constants.Status.NEW;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final WHMProductDao productDao;
    private final WHMProductMapper productMapper;
    private final FilterUtility<WHMProduct, Long> productFilter;

    public ProductServiceImpl(WHMProductDao productDao, WHMProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
        this.productFilter = new FilterUtility<>(productDao);
    }

    @Override
    public ResponseEntity<ResponseDto> addProduct(WHMProductDto productDto) {
        log.info("Inside ProductService: addProduct");
        try {
            productDto.setProductId(null);
            if (productDto.getItem().isEmpty())
                throw new BadRequestAlertException("Item name is required", "error", "error");
            if (productDto.getUnit().isEmpty())
                throw new BadRequestAlertException("Measure unit is required", "error", "error");
            if (productDto.getCategoryId() == null)
                throw new BadRequestAlertException("Category is required", "error", "error");

            productDto.setIsActive(true);
            productDto.setStatusId(NEW);

            productDto = productMapper.toDto(productDao.save(productMapper.toEntity(productDto)));

            return new ResponseEntity<>(new ResponseDto(productDto.getProductId(), "Product added successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in ProductService: addProduct");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<List<WHMProductDto>>> getPaginatedProducts(Integer page, Integer perPage, String search, String sort, String direction) {
        log.info("Inside ProductService: getPaginatedProducts");
        try {
            List<FilterCriteria<WHMProduct>> filterCriteria = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                filterCriteria.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("item").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
                filterCriteria.add((root1, criteriaBuilder) ->
                        (root, query, cb) -> cb.like(
                                cb.lower(root.get("whrProductCategory").get("categoryName").as(String.class)),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            // set filter isActive
            filterCriteria.add((root1, criteriaBuilder) ->
                    (root, query, cb) -> cb.equal(root.get("isActive"), true)
            );

            Page<WHMProduct> paginatedProducts = productFilter.filterRecords(page, perPage, sort, direction, filterCriteria);

            ApiResponseDto<List<WHMProductDto>> response = new ApiResponseDto<>();
            PaginationDto pagination = new PaginationDto();
            pagination.setTotal((int) paginatedProducts.getTotalElements());
            response.setPagination(pagination);
            response.setResult(productMapper.listToDto(paginatedProducts.getContent()));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in ProductService: getPaginatedProducts");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<WHMProductDto> getProduct(Long productId) {
        log.info("Inside ProductService: getProduct");
        try {
            WHMProduct product = productDao.findByProductIdAndIsActive(productId, true).orElseThrow(() -> new BadRequestAlertException("Product not found", "error", "error"));
            return new ResponseEntity<>(productMapper.toDto(product), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in ProductService: getProduct");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> updateProduct(Long productId, WHMProductDto productDto) {
        log.info("Inside ProductService: updateProduct");
        try {
            if (productDto.getProductId() == null)
                throw new BadRequestAlertException("Product ID is required", "error", "error");
            if (!productDto.getProductId().equals(productId))
                throw new BadRequestAlertException("Product ID mismatch", "error", "error");
            if (productDto.getItem().isEmpty())
                throw new BadRequestAlertException("Item name is required", "error", "error");
            if (productDto.getUnit().isEmpty())
                throw new BadRequestAlertException("Measure unit is required", "error", "error");
            if (productDto.getCategoryId() == null)
                throw new BadRequestAlertException("Category is required", "error", "error");

            WHMProduct product = productDao.findByProductIdAndIsActive(productId, true).orElseThrow(() -> new BadRequestAlertException("Product not found", "error", "error"));

            productDto.setIsActive(product.getIsActive());
            productDto.setStatusId(product.getWhrStatus().getStatusId());

            productDto = productMapper.toDto(productDao.save(productMapper.toEntity(productDto)));

            return new ResponseEntity<>(new ResponseDto(productDto.getProductId(), "Product updated successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in ProductService: updateProduct");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> deleteProduct(Long productId) {
        log.info("Inside ProductService: deleteProduct");
        try {
            WHMProduct product = productDao.findByProductIdAndIsActive(productId, true).orElseThrow(() -> new BadRequestAlertException("Product not found", "error", "error"));
            productDao.deleteById(productId);
            return new ResponseEntity<>(new ResponseDto(productId, "Product deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception in ProductService: deleteProduct");
            throw new BadRequestAlertException(e.getMessage(), "error", "error");
        }
    }
}
