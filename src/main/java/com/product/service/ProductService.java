package com.product.service;

import java.util.List;

import com.product.entity.Product;

public interface ProductService { 
	
	public Product addProduct(Product product);
	public Product getProduct(Long productId);
	public List<Product> getAllProducts();
	public String deleteProduct(Long productId);
	public String updateProduct(Long productId, Product product);

}
