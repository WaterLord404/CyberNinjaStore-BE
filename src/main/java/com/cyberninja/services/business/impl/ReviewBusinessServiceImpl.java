package com.cyberninja.services.business.impl;

import org.springframework.stereotype.Service;

import com.cyberninja.model.entity.Review;
import com.cyberninja.services.business.ReviewBusinessServiceI;

@Service
public class ReviewBusinessServiceImpl implements ReviewBusinessServiceI {

	/**
	 * Comprueba si la review creada es valida
	 */
	@Override
	public Boolean isReviewValid(Review review) {
		return review.getValue() >= 1 && 
			   review.getValue() <= 5 &&
			   review.getDetails().length() >= 10 &&
			   review.getDetails().length() <= 255;		
	}

}
