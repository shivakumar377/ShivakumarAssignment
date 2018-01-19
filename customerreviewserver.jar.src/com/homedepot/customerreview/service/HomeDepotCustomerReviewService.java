package com.homedepot.customerreview.service;

import de.hybris.platform.core.model.product.ProductModel;

public interface HomeDepotCustomerReviewService {
	
	public abstract int getReviewsForGivenRangeOfRating(ProductModel product, Double lowestRating,
			Double highestRating);
}
