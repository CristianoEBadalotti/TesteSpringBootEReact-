package com.test.springboot.maven.crud.springbootcrudtest.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String>{
	Comment findById(Long id);

}
