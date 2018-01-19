package com.homedepot.customerreview.dao.impl;

import java.util.Collections;

import com.homedepot.customerreview.dao.HomeDepotCustomerReviewDao;
import com.homedepot.customerreview.dao.ProductModel;

import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

public class DefaultHomeDepotCustomerReviewDao extends AbstractItemDao implements
		HomeDepotCustomerReviewDao {
		
	@Override
	public int getReviewsForGivenRangeOfRating(ProductModel product,Double lowestRating,Double highestRating)
	{
		String query ="SELECT count(*) FROM {" +CustomerReviewModel._TYPECODE + "} WHERE {" + CustomerReviewModel.PRODUCT + "} = ?product AND {" + CustomerReviewModel.RATING + "} >= ?lowestRating AND {" + CustomerReviewModel.RATING + "} <= ?highestRating";
		FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		searchQuery.addQueryParameter("product", product);
		searchQuery.addQueryParameter("lowestRating", lowestRating);
		searchQuery.addQueryParameter("lowestRating", highestRating);
		searchQuery.setResultClassList(Collections.singletonList(Integer.class));
		SearchResult searchResult =getFlexibleSearchService().search(searchQuery);
		return (int) searchResult.getResult().iterator().next();
	}
}
