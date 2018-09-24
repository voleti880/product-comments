package com.target.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.target.model.Comment;
import com.target.model.ErrorInfoException;
import com.target.services.interfaces.CommentsService;

@RestController
public class ProductCommentsController {

	@Resource(name = "commentsService")
	private CommentsService commentsService;

	@PostMapping(value = "/products/{productId}/comments", consumes = "text/plain")
	public ResponseEntity<?> postComment(@PathVariable String productId, @RequestBody String comment) {
		try {
			final String commentId = commentsService.addComment(productId, new Comment(comment));
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentId)
					.toUri();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(location);
			return new ResponseEntity<String>(comment, httpHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			ErrorInfoException exception = (ErrorInfoException) e;
			if (exception.getMessage().contains(productId)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
			}
		}
	}

	@GetMapping("/products/{productId}/comments/{commentId}")
	public Comment getComment(@PathVariable String productId, @PathVariable String commentId) {
		return commentsService.getComment(productId, commentId);
	}

	@GetMapping("/products/{productId}/comments")
	public List<Comment> getAllComments(@PathVariable String productId) {
		return commentsService.getComments(productId);
	}

	@GetMapping("/products")
	public String getProduct() {
		return "Hello Product!";
	}

}
