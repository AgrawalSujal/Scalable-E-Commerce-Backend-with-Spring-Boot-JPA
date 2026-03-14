package com.agrawal.ecomweb.controllers;

import com.agrawal.ecomweb.models.Product;
import com.agrawal.ecomweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping("/")
    public String WelcomeHome(){
        return "Welcome to my Website backend";
    }

    @RequestMapping("/products")
    public ResponseEntity< List<Product>> getAllProducts(){
        return new ResponseEntity<> (service.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/products/{pid}")
    public ResponseEntity<Product> getProductById(@PathVariable int pid){

        Product product = service.getProductById(pid);

        if(product==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(product,HttpStatus.OK);
        }

    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestPart Product productData,
                                                 @RequestPart MultipartFile imagFile) throws IOException {
        Product product = service.addProduct(productData,imagFile);
        try{
            return new ResponseEntity<>(product,HttpStatus.CREATED);
       }catch (Exception e){
            return new ResponseEntity<>(product,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{pid}")
    public ResponseEntity<String> updateProduct(@PathVariable int pid,@RequestBody Product productData,@RequestPart MultipartFile imagFile) throws IOException {
        Product updatedProduct = service.updateProduct(pid,productData,imagFile);

        if(updatedProduct==null){
            return new ResponseEntity<>("Product Not Found!!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Product Updated Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/products/{pid}")
    public ResponseEntity<String> deleteProduct(@PathVariable int pid){
        Product product = service.getProductById(pid);

        if(product != null){
            service.deleteProduct(pid);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/products/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword){
       List<Product> product =service.searchProduct(keyword);

       if(product != null){
           return new ResponseEntity<>(product,HttpStatus.OK);
       }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
