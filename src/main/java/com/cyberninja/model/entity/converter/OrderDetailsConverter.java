package com.cyberninja.model.entity.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cyberninja.model.entity.OrderDetails;
import com.cyberninja.model.entity.dto.OrderDetailsDTO;

@Component
public class OrderDetailsConverter {

	public OrderDetails orderDetailsDTOToOrderDetails(OrderDetailsDTO dto) {
		OrderDetails orderDetails = new OrderDetails();
		
		orderDetails.setUnits(dto.getUnits());
		orderDetails.setColour(dto.getColour());
		orderDetails.setSize(dto.getSize());
		orderDetails.setReturned(false);
		
		return orderDetails;
	}
	
	public OrderDetailsDTO orderDetailsToOrderDetailsDTO(OrderDetails orderDetails) {
		OrderDetailsDTO dto = new OrderDetailsDTO();
		
		dto.setId(orderDetails.getId());
		dto.setUnits(orderDetails.getUnits());
		dto.setColour(orderDetails.getColour());
		dto.setSize(orderDetails.getSize());
		dto.setReturned(orderDetails.isReturned());
		
		return dto;
	}
	
	public List<OrderDetails> orderDetailsDTOToOrderDetails(List<OrderDetailsDTO> dtos) {
		List<OrderDetails> ordersDetails = new ArrayList<>();
		for (OrderDetailsDTO orderDetailsDTO : dtos) {
			ordersDetails.add(orderDetailsDTOToOrderDetails(orderDetailsDTO));
		}
		return ordersDetails;
	}
	
	public List<OrderDetailsDTO> orderDetailsToOrderDetailsDTO(List<OrderDetails> ordersDetails) {
		List<OrderDetailsDTO> dtos = new ArrayList<>();
		for (OrderDetails orderDetails : ordersDetails) {
			dtos.add(orderDetailsToOrderDetailsDTO(orderDetails));
		}
		return dtos;
	}
}
