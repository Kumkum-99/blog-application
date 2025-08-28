package blog.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.model.Post;
import blog.com.model.User;
import blog.com.service.PostService;
import blog.com.service.UserService;
import blog.com.utilis.AuthUtilis;
@RestController
@RequestMapping("/api/posts")
@PreAuthorize("hasRole('USER')")


public class PostController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private AuthUtilis authUtilis;
     private User currentUser;
	
	
	@ModelAttribute
	public void initCUrrentUser() {
	     String email=authUtilis.currentUser();
	     if(email!=null) {
	    	 currentUser=userService.findByEmail(email);
	     }else {
	    	 throw new RuntimeException("User not authenticated");
	     }
		
	}
	
	
	@PostMapping("/")
	public ResponseEntity<Post> addPost(@RequestBody Post post){
		Post savePost=postService.addPost(post, currentUser);
		return new ResponseEntity<>(savePost,HttpStatus.CREATED);
	}
	
	@GetMapping("/mine")
	public ResponseEntity<List<Post>> getOwnPost(){
		List<Post> getAllPost=postService.getAllPostBYUserId(currentUser.getUserId());
		return ResponseEntity.ok(getAllPost); 
	}
	
	@GetMapping("/likeCounts")
	public ResponseEntity<Map<String, Long>> getNumberOfLike(){
		long likes=postService.totalCountsOfLike(currentUser.getUserId());
		return ResponseEntity.ok(Map.of(
				"Total likes",likes));
	}
    @DeleteMapping("/{postId}")
    
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long postId){
    	String userEmail=authUtilis.currentUser();
		postService.deletePostById(postId, userEmail);
		return ResponseEntity.ok("Post deleted");	
    }
   


	


}
