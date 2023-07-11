package com.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.entity.Product;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService { 
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepo.save(product);
	}

	@Override
	public Product getProduct(Long productId) {
		// TODO Auto-generated method stub
		return productRepo.findById(productId).get();
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	public String deleteProduct(Long productId) {
		// TODO Auto-generated method stub
		productRepo.deleteById(productId);
		return "Product Successfully Deleted";
	}

	@Override
	public String updateProduct(Long productId, Product product) {
		// TODO Auto-generated method stub
		Product productFromDb = productRepo.findById(productId).get();
        System.out.println(productFromDb.toString());
        productFromDb.setProductName(product.getProductName());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setProductCategory(product.getProductCategory());
        productFromDb.setProductBrand(product.getProductBrand());
        productFromDb.setProductPrice(product.getProductPrice());
        productRepo.save(productFromDb);
		return "Product Updated Successfully";
	} 
	
}
