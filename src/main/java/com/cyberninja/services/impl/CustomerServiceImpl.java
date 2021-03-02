package com.cyberninja.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberninja.model.entity.Customer;
import com.cyberninja.model.entity.converter.CustomerDTOConverter;
import com.cyberninja.model.entity.dto.CustomerDTO;
import com.cyberninja.model.repository.CustomerRepository;
import com.cyberninja.security.model.entity.User;
import com.cyberninja.security.model.entity.dto.UserDTO;
import com.cyberninja.services.CustomerServiceI;

@Service
public class CustomerServiceImpl implements CustomerServiceI {
	
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CustomerDTOConverter customerConverter;

	/**
	 * Crea un customer
	 */
	@Override
	public Customer createCustomer(CustomerDTO dto, User user) {
		Customer customer = customerConverter.customerDTOToCustomer(dto);
		
		customer.setUser(user);
		user.setCustomer(customer);
		
		customerRepo.save(customer);

		return customer;
	}
	
	/**
	 * Obtiene un customer
	 */
	@Override
	public Customer getCustomer(UserDTO dto) {
		return customerRepo.getCustomerByUserUsername(dto.getUsername());
	}
	
}