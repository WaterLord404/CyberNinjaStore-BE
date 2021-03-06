package com.cyberninja.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cyberninja.model.entity.dto.OrderDTO;
import com.cyberninja.model.entity.dto.OrderDetailsDTO;
import com.cyberninja.model.entity.dto.ReturnDTO;
import com.cyberninja.services.entity.OrderServiceI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/order")
public class OrderController {

	@Autowired private OrderServiceI orderService;
	
	@PostMapping
	public ResponseEntity<OrderDTO> purchaseOrder(
			@RequestBody List<OrderDetailsDTO> dtos, 
			Authentication auth,
			@RequestParam(required = false) String coupon
			) {
		try {
			return ResponseEntity.ok(orderService.purchaseOrder(dtos, auth, coupon));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (NullPointerException | InvalidDataAccessApiUsageException e) {
			throw new ResponseStatusException(BAD_REQUEST);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<OrderDetailsDTO> returnProduct(@RequestBody ReturnDTO dto, Authentication auth) {
		try {
			return ResponseEntity.ok(orderService.returnProduct(dto, auth));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (NullPointerException | InvalidDataAccessApiUsageException e) {
			throw new ResponseStatusException(BAD_REQUEST);
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrdersWithShipping(Authentication auth) {
		try {
			return ResponseEntity.ok(orderService.getOrdersWithShipping(auth));

		} catch (ResponseStatusException e) {
			throw new ResponseStatusException(e.getStatus(), e.getReason());
		} catch (Exception e) {
			throw new ResponseStatusException(INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
