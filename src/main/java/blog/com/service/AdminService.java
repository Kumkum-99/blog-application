package blog.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import blog.com.dto.Userdto;
import blog.com.exception.ResourceNotFound;
import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.User;
import blog.com.repository.PostRepository;
import blog.com.repository.ReactionRepository;
import blog.com.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<Post> getPosts(Long userId) {
        if (userId == null) {
            return postRepository.findAll(); 
        }
        return postRepository.findByAuthor_UserId(userId); 
    }

	
	  public Page<Post> searchPosts(String keyword, Pageable pageable) {
	        return postRepository.searchPosts(keyword, pageable);
	  }
	
	  public void deleteComment(Long reactionId) {
		  Reaction reaction=reactionRepository.findById(reactionId).orElseThrow(()-> new ResourceNotFound("Reaction is not present"));
		  
		  reactionRepository.delete(reaction);
		  
	  }	
	  public User addUser(Userdto userdto) {
           User user=new User();
		  
		  user.setRole(user.getRole().USER);
		  user.setName(userdto.getName());
		  user.setEmail(userdto.getEmail());
		  user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		return   userRepository.save(user);
		 
		}

	
	
}
