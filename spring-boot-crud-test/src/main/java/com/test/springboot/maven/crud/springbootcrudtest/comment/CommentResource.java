package com.test.springboot.maven.crud.springbootcrudtest.comment;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class CommentResource {

	@Autowired
	private CommentRepository commentManagementService;

	@GetMapping("/{username}/comments")
	public List<Comment> getAllComments(@PathVariable String username) {
		return commentManagementService.findAll();
	}

	@GetMapping("/{username}/comments/{id}")
	public Comment getComment(@PathVariable String username, @PathVariable long id) {
		return commentManagementService.findById(id);
	}

	@DeleteMapping("/{username}/comments/{id}")
	public ResponseEntity<Void> deleteComment(@PathVariable String username, @PathVariable long id) {

		Comment comment = commentManagementService.findById(id);
		commentManagementService.delete(comment);

		if (comment != null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{username}/comments/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable String username, @PathVariable long id,
			@RequestBody Comment comment) {

		Comment commentUpdated = commentManagementService.save(comment);

		return new ResponseEntity<Comment>(commentUpdated, HttpStatus.OK);
	}

	@PostMapping("/{username}/comments")
	public ResponseEntity<Void> createComment(@PathVariable String username, @RequestBody Comment comment) {
		
		comment.setId(null);
		Comment createdComment = commentManagementService.save(comment);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdComment.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}