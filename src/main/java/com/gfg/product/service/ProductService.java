package com.gfg.product.service;

import java.util.List;

import com.gfg.product.entity.Product;

public interface ProductService {

	public Product getByUuid(String productUuid);

	public List<Product> getAll();

	public void delete(Product product);

	public Product updateProductByUuid(String uuid, Product product);

	public Product save(Product product);
	
	
}
