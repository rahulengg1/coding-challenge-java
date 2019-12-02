package com.gfg.product.service;

import java.util.List;

import com.gfg.product.entity.Seller;

public interface SellerService {

	public Seller getByUuid(String sellerUuid);
	
	public List<Seller> getTopTen();
}
