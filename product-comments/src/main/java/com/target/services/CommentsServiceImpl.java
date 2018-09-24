package com.target.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.target.filter.TextFilter;
import com.target.model.Comment;
import com.target.model.ErrorInfoException;
import com.target.model.Product;
import com.target.services.interfaces.CommentsService;

@Service("commentsService")
public class CommentsServiceImpl implements CommentsService {

	private SecureRandom random = new SecureRandom();
	private static Map<String, Product> products = new HashMap<String, Product>();

	static {
		products.put("1", new Product());
		products.put("2", new Product());
		products.put("3", new Product());
		products.put("4", new Product());
		products.put("5", new Product());
	}

	public String addComment(final String productId, final Comment comment) throws ErrorInfoException {
		if (!TextFilter.hasObjectionableWords(comment.getComment())) {
			if (products.get(productId) != null) {
				final Comment newComment = comment;
				newComment.setId(new BigInteger(130, random).toString(32));
				products.get(productId).getComments().put(newComment.getId(), newComment);
				return newComment.getId();
			}
			throw new ErrorInfoException("Posting comment failed as Product is invalid - " + productId);
		} else {
			throw new ErrorInfoException("This comment was blocked due to an objectionable word in it.");
		}
	}

	public Comment getComment(String productId, String commentId) {
		if (products.get(productId) != null && products.get(productId).getComments().get(commentId) != null) {
			return products.get(productId).getComments().get(commentId);
		}
		return null;
	}

	public List<Comment> getComments(String productId) {
		if (products.get(productId) != null) {
			return new ArrayList<Comment>(products.get(productId).getComments().values());
		}
		return null;
	}

}
