package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    //create and initialize mock objects.
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;
    private AutoCloseable closeable;
    @BeforeEach
    public void setUp(){
        //this method will scan class and initialize all the annotations and inject them into the service.
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    public void tearDown() {
        //closes resources after every method testing.
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testAddProduct(){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(100.0);
        productDTO.setStockQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStockQuantity(10);

        //saying when the productRepository.save method is called with any product object, then return the product object.
        when(productRepository.save(any(Product.class))).thenReturn(product);

        //calling the addProduct method from the service and passing the productDTO object.
        ProductDTO result = productService.addProduct(productDTO);

        //here 'product' is the predefined product returned from 'when(productRepository.save(any(Product.class))).thenReturn(product);'
        //then addProduct method coverts it back to ProductDTO and returned as a result.
        //then comparing predefined 'product' to the result, since both fields are the same the test will pass.
        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getStockQuantity(), result.getStockQuantity());
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(150.0);
        productDTO.setStockQuantity(15);

        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");
        existingProduct.setPrice(100.0);
        existingProduct.setStockQuantity(10);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        ProductDTO result = productService.updateProduct(id, productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getDescription(), result.getDescription());
        assertEquals(productDTO.getPrice(), result.getPrice());
        assertEquals(productDTO.getStockQuantity(), result.getStockQuantity());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setStockQuantity(10);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(id);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getStockQuantity(), result.getStockQuantity());
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(id);
        });
    }

    @Test
    public void testDeleteID() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleteID(id);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteID_ProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteID(id);
        });
    }

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(100.0);
        product1.setStockQuantity(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(200.0);
        product2.setStockQuantity(20);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(product1.getName(), result.get(0).getName());
        assertEquals(product1.getDescription(), result.get(0).getDescription());
        assertEquals(product1.getPrice(), result.get(0).getPrice());
        assertEquals(product1.getStockQuantity(), result.get(0).getStockQuantity());

        assertEquals(product2.getName(), result.get(1).getName());
        assertEquals(product2.getDescription(), result.get(1).getDescription());
        assertEquals(product2.getPrice(), result.get(1).getPrice());
        assertEquals(product2.getStockQuantity(), result.get(1).getStockQuantity());
    }

    @Test
    public void testGetAllProducts_EmptyList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getAllProducts();
        });
    }

}
