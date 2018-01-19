package com.homedepot.customerreview.dao;

import com.homedepot.customerreview.impl.ProductModel;

public interface HomeDepotCustomerReviewDao{
	
	public abstract int getReviewsForGivenRangeOfRating(ProductModel product,Double lowestRating,Double highestRating);

}
