package com.gfg.product.service;

import com.gfg.product.entity.Product;

public interface NotificationService {

	public void sendPriceChangeWarning(Product product);
}
