package com.example.inventory.service;
import com.example.inventory.dto.ProductResponseDTO;
import com.example.inventory.exception.ProductNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
//import com.example.inventory.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;         //for pagination
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;  //Logger
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductService {

        private final ProductRepository productRepository;
        private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //add product
    public Product addProduct(Product product){
        return productRepository.save(product);
    }
    //Get all products
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    //Get product by id
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id :" + id));
    }
    //Delete product
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public Product updateProduct(Long id, Product updatedproduct){
        //Getting object from the database and assigning it to a variable
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));


            existingProduct.setName(updatedproduct.getName());
            existingProduct.setCategory(updatedproduct.getCategory());
            existingProduct.setPrice(updatedproduct.getPrice());
            existingProduct.setQuantity(updatedproduct.getQuantity());

            return productRepository.save(existingProduct);

    }
    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }
public List<Product> getLowStockProducts(){
        return productRepository.findByQuantityLessThan(5);
}
private ProductResponseDTO mapToDTO(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
}
public ProductResponseDTO getProductDTOById(Long id){
        Product product = getProductById(id);
        return mapToDTO(product);
}
public List<ProductResponseDTO> getAllProductsDTOs(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToDTO)
                .toList();
}
public List<ProductResponseDTO> getProductsByCategoryDTO(String category){
        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(this::mapToDTO)
                .toList();
}
public List<ProductResponseDTO> getLowStockProductDTOs(){
        List<Product> products = productRepository.findByQuantityLessThan(5);

        return products.stream()
                .map(this::mapToDTO)
                .toList();
}
public Page<ProductResponseDTO> getProductDTOsPage(Pageable pageable){
        Page<Product> productsPage = productRepository.findAll(pageable);

        return productsPage.map(this::mapToDTO);
}
public List<ProductResponseDTO> searchProducts(String keyword){
        logger.info("Searching products with keyword: {}", keyword);
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);

        if(products.isEmpty()){
            logger.warn("No products found with keyword: {}", keyword);
            throw new ProductNotFoundException("Product not found with keyword: " + keyword);
        }
        logger.info("Found {} products with keyword: {}", products.size(), keyword);
        return products.stream()
                .map(this::mapToDTO)
                .toList();
}


}































