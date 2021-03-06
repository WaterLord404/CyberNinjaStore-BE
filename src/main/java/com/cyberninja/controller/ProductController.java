package com.cyberninja.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cyberninja.model.entity.dto.ProductDTO;
import com.cyberninja.model.entity.enumerated.ProductCategory;
import com.cyberninja.model.entity.enumerated.ProductColour;
import com.cyberninja.model.entity.enumerated.ProductSize;
import com.cyberninja.services.entity.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/product")
public class ProductController {

	@Autowired
	private ProductServiceI productService;

	/**
	 * Obtiene todos los productos con sus documentos. Puede filtrarse los resultados
	 * 
	 * @return List ProductDTO
	 */
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String filter,
			@RequestParam(required = true) Integer page
			) {
		try {
			return ResponseEntity.ok(productService.getProducts(category, filter, page));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Obtiene un producto con sus documentos
	 * 
	 * @return ProductDTO
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		try {
			return productService.getProductDTO(id).isActive() 
					? ResponseEntity.ok(productService.getProductDTO(id))
					: ResponseEntity.notFound().build();

		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Crea un producto con sus documentos
	 * 
	 * @param String product
	 * @param List MultipartFile images
	 * @return ProductDTO
	 */
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(
			@RequestParam(required = true) String product,
			@RequestParam(required = true) List<MultipartFile> images
			) {
		try {
			// String to object
			ObjectMapper objectMapper = new ObjectMapper();
			ProductDTO dto = objectMapper.readValue(product, ProductDTO.class);
			
			return ResponseEntity.status(CREATED).body(productService.createProduct(dto, images));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (NullPointerException |
				InvalidDataAccessApiUsageException |
				UnrecognizedPropertyException | 
				InvalidFormatException e) {
			throw new ResponseStatusException(BAD_REQUEST);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Borra un producto con sus documentos
	 * 
	 * @return ProductDTO
	 */
	@DeleteMapping
	public ResponseEntity<ProductDTO> deleteProduct(@RequestBody ProductDTO dto) {
		try {
			return ResponseEntity.ok(productService.deleteProduct(dto));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (NullPointerException | InvalidDataAccessApiUsageException e) {
			throw new ResponseStatusException(BAD_REQUEST);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	
	@GetMapping(path = "/size")
	public ResponseEntity<ProductSize[]> getSizes() {
		try {
			return ResponseEntity.ok(productService.getSizes());

		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping(path = "/colour")
	public ResponseEntity<ProductColour[]> getColours() {
		try {
			return ResponseEntity.ok(productService.getColours());

		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping(path = "/category")
	public ResponseEntity<ProductCategory[]> getCategories() {
		try {
			return ResponseEntity.ok(productService.getCategories());

		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
