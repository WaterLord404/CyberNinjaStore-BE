package com.cyberninja.services.entity.impl;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.cyberninja.model.Product;
import com.cyberninja.model.converter.DocumentConverter;
import com.cyberninja.model.converter.ProductConverter;
import com.cyberninja.model.dto.ProductDTO;
import com.cyberninja.repository.ProductRepository;
import com.cyberninja.services.business.InvoiceServiceI;
import com.cyberninja.services.entity.DocumentServiceI;
import com.cyberninja.services.entity.ProductServiceI;

@Service
public class ProductServiceImpl implements ProductServiceI {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductConverter productConverter;

	@Autowired
	private DocumentServiceI documentService;

	@Autowired
	private DocumentConverter documentConverter;
	
	@Autowired
	private InvoiceServiceI invoiceService;

	/**
	 * Obtiene todos los productos con los documentos
	 * 
	 * @return List ProductDTO activos
	 * @throws SQLException
	 */
	@Override
	public List<ProductDTO> getProducts() throws SQLException {
		List<ProductDTO> dtos = new ArrayList<>(); // Lista de productsDTO a retornar

		for (Product product : productRepo.findProductsByActive(true)) {

			ProductDTO productDTO = productConverter.productToProductDTO(product);
			productDTO.setDocuments(documentConverter.getDocumentsDTO(product.getDocuments()));

			dtos.add(productDTO);
		}

		if (dtos.isEmpty()) {
			throw new ResponseStatusException(NOT_FOUND);
		}

		return dtos;
	}

	/**
	 * Obtiene un productDTO con sus documentos
	 * 
	 * @return ProductDTO activo/inactivo
	 * @throws SQLException
	 */
	@Override
	public ProductDTO getProductDTO(Long id) {
		Product product = productRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

		ProductDTO dto = productConverter.productToProductDTO(product);
		dto.setDocuments(documentConverter.getDocumentsDTO(product.getDocuments()));

		return dto;
	}
	
	/**
	 * Obtiene un product activo
	 * 
	 * @return ProductDTO
	 * @throws SQLException
	 */
	@Override
	public Product getProduct(Long id) {
		return productRepo.findProductByIdAndActive(id, true)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
	}

	/**
	 * Crea un producto con sus documentos
	 * 
	 * @return ProductDTO
	 * @throws SQLException
	 */
	@Override
	public ProductDTO createProduct(ProductDTO dto, List<MultipartFile> images) throws SQLException {
		Product product = productConverter.productDTOToProduct(dto);

		// Calcula iva y descuento
		product = invoiceService.calculateInvoice(product);
		
		// Añade las imagenes
		product.setDocuments(documentService.createDocuments(images, product));

		productRepo.save(product);

		return productConverter.productToProductDTO(product);
	}

	/**
	 * Inactiva un producto
	 * 
	 */
	@Override
	public void deleteProduct(ProductDTO dto) {
		Product product = getProduct(dto.getId());

		product.setActive(false);
		productRepo.save(product);
	}

}