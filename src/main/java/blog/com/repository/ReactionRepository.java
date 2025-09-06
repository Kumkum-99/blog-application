package blog.com.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.ReactionType;
import blog.com.model.User;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
Optional<Reaction> findByUserAndPostAndType(User user, Post post, ReactionType type);
}








