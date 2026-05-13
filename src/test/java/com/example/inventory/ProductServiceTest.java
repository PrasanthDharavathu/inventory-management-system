package com.example.inventory;
import com.example.inventory.dto.ProductResponseDTO;
import com.example.inventory.exception.ProductNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.inventory.exception.ProductNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void searchProducts_ShouldReturnProducts_WhenKeywordExists(){
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setCategory("Stationery");
        product.setPrice(5);
        product.setQuantity(20);

        Mockito.when(productRepository.findByNameContainingIgnoreCase("note"))
                .thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.searchProducts("note");

        assertEquals(1, result.size());
        assertEquals("Notebook", result.get(0).getName());
        assertEquals("Stationery", result.get(0).getCategory());
        assertEquals(5, result.get(0).getPrice());

    }
    @Test
    void searchProducts_ShouldThrowError_WhenKeywordNotFound(){
        Mockito.when(productRepository.findByNameContainingIgnoreCase("xyz"))
                .thenReturn(List.of());
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.searchProducts("xyz")
        );
        assertEquals("Product not found with keyword: xyz", exception.getMessage());
    }

}
