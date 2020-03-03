package com.test.springboot.maven.crud.springbootcrudtest;

import org.assertj.core.api.Assertions;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.springboot.maven.crud.springbootcrudtest.comment.Comment;
import com.test.springboot.maven.crud.springbootcrudtest.comment.CommentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class TestesUnitariosComment {

	@Autowired
	private CommentRepository commentRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createShouldPersistData() {
		Comment comment = new Comment("Cris", "Teste Spring Boot", 1);
		this.commentRepository.save(comment);
		Assertions.assertThat(comment.getId()).isNotNull();
		Assertions.assertThat(comment.getUsername()).isEqualTo("Cris");
		Assertions.assertThat(comment.getDescription()).isEqualTo("Teste Spring Boot");
		Assertions.assertThat(comment.getUpvotes()).isNotNull();
		
		comment = new Comment("", "Teste Spring Boot", 1);
		this.commentRepository.save(comment);
		Assertions.assertThat(comment.getId()).isNotNull();
		Assertions.assertThat(comment.getUsername()).isEqualTo("");
		Assertions.assertThat(comment.getDescription()).isEqualTo("Teste Spring Boot");
		Assertions.assertThat(comment.getUpvotes()).isNotNull();
	}
	
	@Test
	public void deleteShouldRemoveData() {
		Comment comment = new Comment("Cris", "Teste Spring Boot", 1);
		this.commentRepository.save(comment);
		this.commentRepository.delete(comment);
		Assertions.assertThat(this.commentRepository.findById(comment.getId())).isNull();
	}
	
	@Test
	public void updateShouldChangeAndPersistData() {
		Comment comment = new Comment("Cris", "Teste Spring Boot", 1);
		this.commentRepository.save(comment);
		comment.setUsername("");
		comment.setDescription("Isso foi alterado");
		comment.setUpvotes(-1);
		this.commentRepository.save(comment);
		comment = this.commentRepository.findById(comment.getId());
		Assertions.assertThat(comment.getUsername()).isEqualTo("");
		Assertions.assertThat(comment.getDescription()).isEqualTo("Isso foi alterado");
		Assertions.assertThat(comment.getUpvotes()).isNotNull();
	}
	
	@Test
	public void createWhenDescriptionIsNullShouldThrowConstraintViolationExxception() {
		this.thrown.expect(ConstraintViolationException.class);
		this.thrown.expectMessage("O campo texto é obrigatório");
		this.commentRepository.save(new Comment());
	}
	
	
}
