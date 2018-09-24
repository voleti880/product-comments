package com.target.services.interfaces;

import java.util.List;

import com.target.model.Comment;
import com.target.model.ErrorInfoException;

public interface CommentsService {

	public String addComment(final String productId, final Comment comment) throws ErrorInfoException;

	public Comment getComment(String productId, String commentId);

	public List<Comment> getComments(final String productId);
}
