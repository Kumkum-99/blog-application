package blog.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.model.Comment;

public interface CommentRepositoty extends JpaRepository<Comment, Long>{

}
