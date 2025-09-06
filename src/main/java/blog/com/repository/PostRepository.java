package blog.com.repository;

import java.util.List;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import blog.com.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JpaRepository<Post, Long> {
	public List<Post> findByAuthor_UserId(Long id);

    @EntityGraph(attributePaths = "reactions")
    List<Post> findAll();
    

    @Query("SELECT p FROM Post p " +
           "WHERE (:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(p.author.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);

}







