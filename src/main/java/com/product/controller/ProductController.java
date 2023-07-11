package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.product.entity.Product;
import com.product.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/product")
public class ProductController { 
	
	@Autowired
	private ProductService productServ;
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		return new ResponseEntity<Product> (productServ.addProduct(product), HttpStatus.CREATED);
	}
	
	@GetMapping("/getProduct/{productId}")
	@HystrixCommand(fallbackMethod = "fallback_getProduct", commandProperties = {
	        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
	})
	public ResponseEntity<?> getProduct(@PathVariable Long productId){
		return new ResponseEntity<Product> (productServ.getProduct(productId), HttpStatus.OK);
	}
	
	@GetMapping("/getProduct")
	@HystrixCommand(fallbackMethod = "fallback_getAllProducts", commandProperties = {
	        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
	})
	public ResponseEntity<?> getAllProducts() {
		return new ResponseEntity<List<Product>> (productServ.getAllProducts(), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		return new ResponseEntity<String> (productServ.deleteProduct(productId), HttpStatus.OK);
	}
	
	@PutMapping("/updateProduct/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
		productServ.updateProduct(productId, product);
		return new ResponseEntity<Product> (productServ.getProduct(productId), HttpStatus.OK);
	}
	
	private ResponseEntity<?> fallback_getProduct(Long productId) {
        // Fallback response when getProduct fails
        return new ResponseEntity<String>("Unable to fetch product. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private ResponseEntity<?> fallback_getAllProducts() {
        // Fallback response when getAllProducts fails
        return new ResponseEntity<String>("Unable to fetch products. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
