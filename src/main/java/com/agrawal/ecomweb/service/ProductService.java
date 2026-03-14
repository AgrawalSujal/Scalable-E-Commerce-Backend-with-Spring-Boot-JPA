package com.agrawal.ecomweb.service;

import com.agrawal.ecomweb.models.Product;
import com.agrawal.ecomweb.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int pid) {
        return repo.findById(pid).orElse(null);
    }

    public Product addProduct(Product productData, MultipartFile imagFile) throws IOException {
        productData.setImageName(imagFile.getOriginalFilename());
        productData.setImageType(imagFile.getContentType());
        productData.setImageDate(imagFile.getBytes());
        return repo.save(productData);
    }

    public Product updateProduct(int pid, Product productData,MultipartFile imagFile) throws IOException {
        productData.setImageName(imagFile.getOriginalFilename());
        productData.setImageType(imagFile.getContentType());
        productData.setImageDate(imagFile.getBytes());
        return repo.save(productData);
    }

    public void deleteProduct(int pid) {
        repo.deleteById(pid);
    }

    public List<Product> searchProduct(String keyword) {
        return repo.searchProducts(keyword);
    }
}
