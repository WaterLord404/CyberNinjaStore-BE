package com.cyberninja.services.entity.impl;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cyberninja.model.entity.Customer;
import com.cyberninja.model.entity.converter.CustomerConverter;
import com.cyberninja.model.entity.dto.CustomerDTO;
import com.cyberninja.model.repository.CustomerRepository;
import com.cyberninja.security.model.entity.User;
import com.cyberninja.security.model.entity.dto.UserDTO;
import com.cyberninja.services.entity.CustomerServiceI;
import com.cyberninja.services.utils.EmailSenderServiceI;

@Service
public class CustomerServiceImpl implements CustomerServiceI {
	
	@Autowired private CustomerRepository customerRepo;

	@Autowired private CustomerConverter customerConverter;
	
	@Autowired private EmailSenderServiceI emailSender;

	/**
	 * Crea un customer
	 * @throws MessagingException 
	 */
	@Override
	public Customer createCustomer(CustomerDTO dto, User user) throws MessagingException {
		Customer customer = customerConverter.customerDTOToCustomer(dto);
	
		customer.setUser(user);
		user.setCustomer(customer);
		
		customerRepo.save(customer);

		// Envia el correo
		emailSender.sendEmail(customer.getEmail(), user.getConfirmationToken());
		
		return customer;
	}
	
	/**
	 * Obtiene un customer
	 */
	@Override
	public Customer getCustomer(UserDTO dto) {
		return customerRepo.getCustomerByUserUsername(dto.getUsername());
	}
	
	@Override
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Customer not found"));
	}
}
