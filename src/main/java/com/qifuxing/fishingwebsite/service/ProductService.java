package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages business logic for managing products.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    //for list method to return product, we want to convert to the productdto class first to allow flexibility for
    //future modifications and limit data exposure to clients to protect sensitive fields if have any.
    public ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockQuantity(product.getStockQuantity());
        //logger.info("Converted to ProductDTO: {}", productDTO);
        return productDTO;
    }

    public Product convertToEntity(ProductDTO productDTO){
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        //logger.info("Converted to Product: {}", product);
        return product;
    }

    /*
    //this is the longer version
    public List<ProductDTO> getAllProducts(){
        //get all products
        List<Product> product = productRepository.findAll();
        //create productDTO list
        List<ProductDTO> productDTOList = new ArrayList<>();

        //go through each product then convert it to productDto and add it to productDTOList and return it.
        for (Product product1 : product){
            ProductDTO productDTO = convertToDTO(product1);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }
     */

    //using stream for collections of data like list, sets and others to perform filtering,mapping, and reducing on a
    //collection, which makes it more readable and concise.
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ResourceNotFoundException("Empty list, no product in database");
        }
        //logger.info("Retrieved products: {}", products);
        //usually .stream() is called after a collection method in this case its findAll() and converts the list of
        //products into a stream but product already is the result of findAll() method.
        List<ProductDTO> productDTOList =  products.stream()
                //map function here converts this which is products into productDto object
                .map(this::convertToDTO)
                //collects the elements that are now transformed into productDto and back into a list, then the type of
                //list is determine specifically in the method signature which is 'List<ProductDTO>' in this case.
                .collect(Collectors.toList());
        //logger.info("Converted ProductDTO List: {}", productDTOList);
        return productDTOList;
    }

    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                //.orElseThrow is a method from Optional<> class even though not written but from the method we know
                //from Optional class to check if null.
                //'() ->' is a lambda expression and in this case implements 'Supplier' interface that provides the
                // 'get()' to get exception to be thrown if 'Optional' class is empty.
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    public ProductDTO addProduct(ProductDTO productDTO){
        /*
        //the exception here need to change from null to something else in the future as if body request is null or
        //empty it won't even reach controller class which means it won't reach service class and would not reach this
        //method as the 'HttpMessageNotReadableException' happens before it reaches the controller class if the
        //request body is empty and nothing is being sent from http.
        if (productDTO == null){
            System.out.println("Invalid input: " + productDTO);
            throw new InvalidInputException("Invalid data request");
        }
         */
        Product product = convertToEntity(productDTO);
        //logger.info("Converted ProductDTO to Product: {}", product);
        Product newProduct = productRepository.save(product);
        //logger.info("Saved new product: {}", newProduct);
        ProductDTO productDTO1 = convertToDTO(newProduct);
        //logger.info("Converted saved product to ProductDTO: {}", productDTO1);
        return productDTO1;
    }

    public ProductDTO updateProduct(Long id,ProductDTO productDTO){
        //findById() WILL return 'Optional<T>' so need to handle exception accordingly.
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setId(productDTO.getId());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    public void deleteID(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }
}