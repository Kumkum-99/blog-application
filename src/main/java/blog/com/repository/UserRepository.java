package blog.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
