package com.example.inventory.controller;

import com.example.inventory.dto.ProductRequestDTO;
import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.example.inventory.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://inventory-management-system-two-azure.vercel.app"
})
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
@PostMapping
public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequestDTO dto){

        Product product = new Product();

        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        Product savedProduct = productService.addProduct(product);
        URI location = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(location).body(savedProduct);
}
   /* public Product addProduct(@Valid @RequestBody Product product){
        return productService.addProduct(product);
}*/
@GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
}
@GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id){
        return productService.getProductDTOById(id);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
}
//    public void deleteProduct(@PathVariable Long id){
//        productService.deleteProduct(id);
//}
@PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto){

    Product product = new Product();

    product.setName(dto.getName());
    product.setCategory(dto.getCategory());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
        return productService.updateProduct(id, product);
}
@GetMapping("/category/{category}")
    public List<ProductResponseDTO> getProductsByCategory(@PathVariable String category){
    return productService.getProductsByCategoryDTO(category);
}
@GetMapping("/low-stock")
    public List<ProductResponseDTO> getLowStockProducts(){
    return productService.getLowStockProductDTOs();
}
@GetMapping("/page")
    public Page<ProductResponseDTO> getProdutsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){

    Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);

    return productService.getProductDTOsPage(pageable);
}
@GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(@RequestParam String keyword){
    return productService.searchProducts(keyword);
}
@GetMapping("/category/{category}/page")
    public Page<ProductResponseDTO> getProductsByCategoryPage(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

    Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return productService.getProductsByCategoryPage(category, pageable);
}

@GetMapping("/search/page")
    public Page<ProductResponseDTO> searchProductsPage(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
    Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return productService.searchProductsPage(keyword, pageable);
}

@GetMapping("/filter")
    public Page<ProductResponseDTO> filterProducts(
            @RequestParam String keyword,
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
    Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);
    return productService.filterProducts(keyword, category, pageable);
}
}





















