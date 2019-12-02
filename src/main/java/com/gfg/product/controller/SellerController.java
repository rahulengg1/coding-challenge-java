package com.gfg.product.controller;

import com.gfg.product.entity.Seller;
import com.gfg.product.exception.ResourceNotFoundException;
import com.gfg.product.service.SellerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SellerController {

	private SellerService sellerService;

	public SellerController(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@ApiOperation(value = "View seller information by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = Seller.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = "/v1/sellers/{uuid}",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Seller> getSellerById(@PathVariable @NotNull String uuid) {
		Seller seller = sellerService.getByUuid(uuid);
		if (seller == null) {
			throw new ResourceNotFoundException("Seller", "UUID", uuid);
		}

		return new ResponseEntity<>(seller, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "View seller information by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = Seller.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = "/v2/sellers/{uuid}",
	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Seller> getSellerByIdForLink(@PathVariable @NotNull String uuid) {
		Seller seller = sellerService.getByUuid(uuid);
		if (seller == null) {
			throw new ResourceNotFoundException("Seller", "UUID", uuid);
		}

		return new ResponseEntity<>(seller, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "View top 10 seller information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = Seller.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = "/v2/sellers/top10", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Seller>> getTopTenSeller() {
		List<Seller> sellers = sellerService.getTopTen();

		return new ResponseEntity<>(sellers, HttpStatus.OK);
	}
}
