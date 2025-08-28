package blog.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	public List<Post> findByAuthor_UserId(Long id);

    @EntityGraph(attributePaths = "reactions")
    List<Post> findAll();

}




