package com.cyberninja.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.cyberninja.services.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/product")
public class ProductController {

	@Autowired
	private ProductServiceI productService;

	/**
	 * Obtiene todos los productos con sus documentos
	 * 
	 * @return List ProductDTO
	 */
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getProducts() {
		try {
			return ResponseEntity.ok(productService.getProducts());

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus());
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
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
			return ResponseEntity.ok(productService.getProduct(id));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus());
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Crea un producto con sus documentos
	 * 
	 * @param String product
	 * @param List   MultipartFile images
	 * @return ProductDTO
	 */
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestParam(required = true) String product,
			@RequestParam(required = true) List<MultipartFile> images) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			return ResponseEntity.status(CREATED)
					.body(productService.createProduct(
							objectMapper.readValue(product, ProductDTO.class), images));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus());
		} catch (NullPointerException |
				InvalidDataAccessApiUsageException |
				UnrecognizedPropertyException e) {
			throw new ResponseStatusException(BAD_REQUEST);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Borra un producto con sus documentos
	 * 
	 * @return ProductDTO
	 */
	@DeleteMapping
	public ResponseEntity<Object> deleteProduct(@RequestBody ProductDTO dto) {
		try {
			productService.deleteProduct(dto);
			return ResponseEntity.ok().build();

		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(NOT_FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
		}
	}

}