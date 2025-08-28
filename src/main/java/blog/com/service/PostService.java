package blog.com.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.ReactionType;
import blog.com.model.User;
import blog.com.repository.PostRepository;
import blog.com.repository.UserRepository;
@Component
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public List<Post> getAllPostBYUserId(Long id){
	List<Post> posts=postRepository.findByAuthor_UserId(id);
	return posts;
	}
	
	public long totalCountsOfLike(Long UserId) {
	List<Post>posts =getAllPostBYUserId(UserId);
	  return posts.stream()
              .flatMap(post -> post.getReactions().stream())
              .filter(reaction -> reaction.getType() == ReactionType.LIKE)
              .count();
	
	
	}
	
	 public Post addPost(Post post,User user) {
		post.setAuthor(user);
		post.setCreateAt(LocalDateTime.now() );
		return   postRepository.save(post);  
		  
	  }
	 
	 public void   deletePostById(Long id,String email ) {
		 
		 User user=userRepository.findByEmail(email);
		 Post post=postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post Not found with id:"+ id));
         if(!post.getAuthor().getUserId().equals(user.getUserId())) {
             throw new RuntimeException("You are not authorized to delete this post");
        	 
         }
		  
		 postRepository.delete(post);
		
		
		 
	 }
	 


}
