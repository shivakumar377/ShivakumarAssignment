package com.homedepot.customerreview.service.impl;

import org.apache.bcel.verifier.exc.AssertionViolatedException;

import com.homedepot.customerreview.service.HomeDepotCustomerReviewService;
import com.homedepot.customerreview.dao.HomeDepotCustomerReviewDao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;

public class DefaultHomeDepotCustomerReviewService extends
		DefaultCustomerReviewService implements HomeDepotCustomerReviewService{

	private static final String CURSE_WORDS_KEY="reviews.cursewords.list";
	private static final String STRING_COMMA=",";
	private HomeDepotCustomerReviewDao homeDepotCustomerReviewDao;
	
	public HomeDepotCustomerReviewDao getHomeDepotCustomerReviewDao() {
		return homeDepotCustomerReviewDao;
	}
	
	/*This method will create the customer review based on some of pre condition
	 *  i.e rating should be >0 and comments should not contains the curse words */
	@Override
	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product)
	{
		String []curseWords=Config.getParameter(CURSE_WORDS_KEY).split(STRING_COMMA);
		ServicesUtil.validateParameterNotNullStandardMessage("Rating", rating);
		if(rating < 0)
		{
			throw new RuntimeException("Rating cannot be less than zero"+rating);
		}
		for(String curseWord:curseWords)
		{
			if(StringUtils.isNotEmpty(comment) && comment.contains(curseWord))	
			{
				throw new RuntimeException("Curse words not allowed in comments"+curseWord);
			}
		}
		CustomerReview review = CustomerReviewManager.getInstance().createCustomerReview(rating, headline, comment, 
		(User)getModelService().getSource(user), (Product)getModelService().getSource(product));
		return (CustomerReviewModel)getModelService().get(review);

	}
	
	//this method willreturn the count within given ratings ranges
	@Override
	public int getReviewsForGivenRangeOfRating(ProductModel product, Double lowestRating,
			Double highestRating) {
		ServicesUtil.validateParameterNotNullStandardMessage("product", product);
		ServicesUtil.validateParameterNotNullStandardMessage("lowestRating", lowestRating);
		ServicesUtil.validateParameterNotNullStandardMessage("highestRating", highestRating);
		return getHomeDepotCustomerReviewDao().getReviewsForGivenRangeOfRating(product, lowestRating, highestRating);
	}

}
