package com.gfg.product.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"uuid"})
public class SellerObject extends ResourceSupport {

	private String uuid;
}
