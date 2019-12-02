package com.gfg.product.controller;

import com.gfg.product.dto.ProductDTO;
import com.gfg.product.entity.Product;
import com.gfg.product.exception.ResourceNotFoundException;
import com.gfg.product.model.ProductResource;
import com.gfg.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@Api(value = "Products")
public class ProductController {

	private static final String ENDPOINT_V1 = "/v1/products";
	private static final String ENDPOINT_V2 = "/v2/products";
	private static final String UUID_ENDPOINT_V1 = "/v1/products/{uuid}";
	private static final String UUID_ENDPOINT_V2 = "/v2/products/{uuid}";

	private ProductService productService;

	private static final ModelMapper mapper = new ModelMapper();

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@ApiOperation(value = "View product information by UUID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Processed successfully", response = ProductDTO.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = UUID_ENDPOINT_V1, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductBUuid(@PathVariable String uuid) {
		Product product = productService.getByUuid(uuid);
		if (product == null) {
			throw new ResourceNotFoundException("Product", "UUID", uuid);
		}

		return new ResponseEntity<>(mapper.map(product, ProductDTO.class), HttpStatus.OK);
	}

	@ApiOperation(value = "View all products")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = ProductDTO.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = ENDPOINT_V1, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<Product> products = productService.getAll();

		List<ProductDTO> productDTOList = products.stream()
				.map(product -> mapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());

		return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete ane product by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = Object.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@DeleteMapping(value = { UUID_ENDPOINT_V1, UUID_ENDPOINT_V2 }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable String uuid) {
		Product product = productService.getByUuid(uuid);
		if (product == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		productService.delete(product);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update one product by UUID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Processed successfully", response = ProductDTO.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found (Seller or Product)") 
	})
	@PutMapping(value = { UUID_ENDPOINT_V1, UUID_ENDPOINT_V2 }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> update(@PathVariable String uuid, @Valid @RequestBody ProductDTO productDTO) {
		Product product = mapper.map(productDTO, Product.class);

		return new ResponseEntity<>(
				mapper.map(
					productService.updateProductByUuid(uuid, product),
					ProductDTO.class),
				HttpStatus.OK
		);
	}

	@ApiOperation(value = "Create a new product")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Processed successfully", response = ProductDTO.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found (Seller)") 
	})
	@PostMapping(value = { ENDPOINT_V1, ENDPOINT_V2 }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity create(@Valid @RequestBody ProductDTO productDTO) {
		Product product = mapper.map(productDTO, Product.class);
		productService.save(product);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "View product information by UUID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = ProductResource.class),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = UUID_ENDPOINT_V2, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource<ProductResource>> getProductBUuidWithLink(@PathVariable String uuid) {
		Product product = productService.getByUuid(uuid);
		if (product == null) {
			throw new ResourceNotFoundException("Product", "UUID", uuid);
		}
	
		ProductResource productResource = new ProductResource(product);
		Resource<ProductResource> resource = new Resource<>(productResource);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@ApiOperation(value = "View all products")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Processed successfully", response = ProductResource.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@GetMapping(value = ENDPOINT_V2, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<ProductResource>> getAllProductsWithLink() {

		List<ProductResource> productList = productService.getAll()
				.stream().map(ProductResource::new)
				.collect(Collectors.toList());
		Resources<ProductResource> resources = new Resources<>(productList);
	
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

}
