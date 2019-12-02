package com.gfg.product.service.impl;

import com.gfg.product.entity.Seller;
import com.gfg.product.repository.SellerRepository;
import com.gfg.product.service.SellerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

	private SellerRepository repository;

	public SellerServiceImpl(SellerRepository repository) {
		this.repository = repository;
	}

	public Seller getByUuid(String sellerUuid) {
		List<Seller> sellers = this.repository.findByUuid(sellerUuid);

		return sellers.size() == 0 ? null : sellers.get(0);
	}

	public List<Seller> getTopTen() {
	
		return this.repository.findTopTen();
	}
}
