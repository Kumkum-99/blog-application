package blog.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long>{

}
