package com.cyberninja.services.entity.impl;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cyberninja.model.entity.Discount;
import com.cyberninja.model.entity.Product;
import com.cyberninja.model.entity.converter.DiscountConverter;
import com.cyberninja.model.entity.converter.ProductConverter;
import com.cyberninja.model.entity.dto.DiscountDTO;
import com.cyberninja.model.entity.dto.ProductDTO;
import com.cyberninja.model.repository.DiscountRepository;
import com.cyberninja.model.repository.ProductRepository;
import com.cyberninja.services.business.OrderDetailsBusinessServiceI;
import com.cyberninja.services.entity.DiscountServiceI;
import com.cyberninja.services.entity.ProductServiceI;

@Service
public class DiscountServiceImpl implements DiscountServiceI {

	@Autowired
	private DiscountRepository discountRepo;

	@Autowired
	private DiscountConverter discountConverter;

	@Autowired
	private ProductServiceI productService;

	@Autowired
	private ProductConverter productConverter;

	@Autowired
	private OrderDetailsBusinessServiceI invoiceService;
	
	@Autowired
	private ProductRepository productRepo;
	
	/**
	 * Crea un descuento
	 */
	@Override
	public DiscountDTO addDiscount(DiscountDTO dto) {
		Discount discount = discountConverter.discountDTOToDiscount(dto);

		discountRepo.save(discount);

		return discountConverter.discountToDiscountDTO(discount);
	}

	/**
	 * Obtiene un descuento activo
	 */
	@Override
	public Discount getDiscount(Long id) {
		return discountRepo.findDiscountByIdAndActive(id, true)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
	}

	/**
	 * Actualiza el descuetno de un producto
	 */
	@Override
	public ProductDTO setDiscount(Long productId, Long discountId, Boolean active) {
		Product product = productService.getProduct(productId);

		if (!active) {
			product.setDiscount(null);
		}
		product.setDiscount(getDiscount(discountId));

		// Calcula iva y descuento
		product = invoiceService.calculateInvoice(product);
		
		productRepo.save(product);

		return productConverter.productToProductDTO(product);
	}

}
