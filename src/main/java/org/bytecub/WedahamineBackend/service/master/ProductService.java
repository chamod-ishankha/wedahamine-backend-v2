package org.bytecub.WedahamineBackend.service.master;

import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.master.WHMProductDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ResponseDto> addProduct(WHMProductDto productDto);

    ResponseEntity<ApiResponseDto<List<WHMProductDto>>> getPaginatedProducts(Integer page, Integer perPage, String search, String sort, String direction);

    ResponseEntity<WHMProductDto> getProduct(Long productId);

    ResponseEntity<ResponseDto> updateProduct(Long productId, WHMProductDto productDto);

    ResponseEntity<ResponseDto> deleteProduct(Long productId);
}
