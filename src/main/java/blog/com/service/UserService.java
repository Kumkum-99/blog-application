package blog.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import blog.com.dto.Userdto;
import blog.com.exception.ResourceNotFound;
import blog.com.model.Post;

import blog.com.model.User;
import blog.com.repository.PostRepository;
import blog.com.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private PostRepository postRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  
 
  public User save(Userdto userdto) {
	  User user=new User();
	  
	  user.setRole(user.getRole().USER);
	  user.setName(userdto.getName());
	  user.setEmail(userdto.getEmail());
	  user.setPassword(passwordEncoder.encode(userdto.getPassword()));
	return   userRepository.save(user);
	  
  }
  public User findUserById(Long id) {
	User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFound("User with ths id not found"));
	return user;
  }
  
  public User findByEmail(String email) {
	  User user=userRepository.findByEmail(email);
	  return user;
  }
	 public List<Post> getAllPost(){
		 List<Post>getAllPost=postRepository.findAll();
		 return getAllPost;
	 }
  
 
   

	 
	 
   
	

}
