package com.gfg.product.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gfg.product.controller.SellerController;
import com.gfg.product.entity.Product;

import lombok.Getter;

@Getter
@JsonPropertyOrder({ "brand", "name", "seller", "stock", "uuid" })
public class ProductResource {

	@JsonIgnore
	private final Product product;

	private SellerObject seller;

	public String getBrand() {
		return this.product.getBrand();
	}

	public String getName() {
		return this.product.getName();
	}

	public String getUuid() {
		return this.product.getUuid();
	}

	public Integer getStock() {
		return this.product.getStock();
	}

	public ProductResource(Product product) {
		super();
		this.product = product;
		this.seller = new SellerObject();
		seller.setUuid(product.getUuid());
		seller.add(linkTo(methodOn(SellerController.class).getSellerByIdForLink(product.getUuid())).withSelfRel());
	}

}
