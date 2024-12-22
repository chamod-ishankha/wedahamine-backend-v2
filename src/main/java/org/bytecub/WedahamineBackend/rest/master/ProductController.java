package org.bytecub.WedahamineBackend.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.master.WHMProductDto;
import org.bytecub.WedahamineBackend.dto.miscellaneous.ApiResponseDto;
import org.bytecub.WedahamineBackend.service.master.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> addProduct(@RequestBody WHMProductDto productDto) {
        log.info("Inside ProductController: addProduct");
        return productService.addProduct(productDto);
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDto<List<WHMProductDto>>> getPaginatedProducts(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer per_page,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "productId", required = false) String sort,
            @RequestParam(defaultValue = "asc", required = false) String direction
    ) {
        log.info("Inside ProductController: getPaginatedProducts");
        return productService.getPaginatedProducts(page, per_page, search, sort, direction);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<WHMProductDto> getProduct(@PathVariable Long productId) {
        log.info("Inside ProductController: getProduct");
        return productService.getProduct(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable Long productId, @RequestBody WHMProductDto productDto) {
        log.info("Inside ProductController: updateProduct");
        return productService.updateProduct(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable Long productId) {
        log.info("Inside ProductController: deleteProduct");
        return productService.deleteProduct(productId);
    }
}
