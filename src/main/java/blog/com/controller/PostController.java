package blog.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import blog.com.dto.ReactionResponseDto;
import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.User;
import blog.com.service.PostService;
import blog.com.service.ReactionService;
import blog.com.service.UserService;
import blog.com.utilis.AuthUtilis;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/posts")
@Tag(name="POST API" )
@PreAuthorize("hasRole('USER')")


public class PostController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostService postService;
    @Autowired
    private ReactionService reactionService;
    
    @Autowired
    private AuthUtilis authUtils;
     
	
	
     private User getCurrentUser() {
	        String email = authUtils.currentUser();
	        return userService.findByEmail(email);
	                
	    }
	
	@PostMapping("/")
	public ResponseEntity<Post> addPost(@RequestBody Post post){
		User currentUser=getCurrentUser();
		Post savePost=postService.addPost(post, currentUser);
		return new ResponseEntity<>(savePost,HttpStatus.CREATED);
	}
	
	@GetMapping("/own")
	public ResponseEntity<List<Post>> getOwnPost(){
		User currentUser=getCurrentUser();
		List<Post> getAllPost=postService.getAllPostBYUserId(currentUser.getUserId());
		return ResponseEntity.ok(getAllPost); 
	}
	@PostMapping("/{postId}/like")
	public ResponseEntity<String> toggleLike(
	        @PathVariable("postId") Long postId
	       ){
		
		User currentUser=getCurrentUser();
	    Reaction result = reactionService.addLike(currentUser, postId);
	    
	    if (result == null) {
	        return ResponseEntity.ok("Like removed");
	    } else {
	        return ResponseEntity.ok("Like added");
	    }
	}
	 @PostMapping("/{postId}/comment")
		public ResponseEntity<?> newComment(@PathVariable("postId") Long postId, @RequestBody Reaction requestReaction){
			User currentUser=getCurrentUser();
			Reaction reaction=reactionService.addComment(currentUser, postId, requestReaction.getCommentText());
			  ReactionResponseDto response = new ReactionResponseDto(
			            reaction.getReactionId(),
			            reaction.getType().name(),
			            reaction.getCommentText(),
			            reaction.getUser().getUserId(),
			            reaction.getUser().getName(),
			            reaction.getPost().getPostId(),
			            reaction.getPost().getTitle()
			    );
			
			return new ResponseEntity<>(response,HttpStatus.OK);

	  
		}
	
	@GetMapping("/likeCounts")
	public ResponseEntity<Map<String, Long>> getNumberOfLike(){
		User currentUser=getCurrentUser();
		long likes=postService.totalCountsOfLike(currentUser.getUserId());
		return ResponseEntity.ok(Map.of(
				"Total likes",likes));
	}
	@GetMapping("/commentsCounts")
	public ResponseEntity<Map<String, Long>> getNumberOfComments(){
		User currentUser=getCurrentUser();
		long likes=postService.totalCountsOfComments(currentUser.getUserId());
		return ResponseEntity.ok(Map.of(
				"Total comments",likes));
	}
    @DeleteMapping("/{postId}")
    
    public ResponseEntity<String> deletePostById(@PathVariable("postId") Long postId){
    	String userEmail=authUtils.currentUser();
		postService.deletePostById(postId, userEmail);
		return ResponseEntity.ok("Post deleted");	
    }
   
	@DeleteMapping("/delete/comment/{reactionId}")
	public ResponseEntity<?> deleteComment(@PathVariable("reactionId") Long reactionId){
		User currentUser=getCurrentUser();
		reactionService.deleteComment(reactionId,currentUser);
		return ResponseEntity.ok("Comment deleted");
	}
	@GetMapping("/{postId}/likes/count")
	public ResponseEntity<Long> getLikeCount(@PathVariable("postId")Long postId){
		return ResponseEntity.ok(reactionService.countLikes(postId));
	}
	
	@PutMapping("/comments/{reactionId}")
	public ResponseEntity<Reaction> updateComment(@PathVariable("reactionId") Long reactionId,@RequestBody Reaction requestReaction){
		Reaction reaction=reactionService.updateComment(reactionId ,requestReaction.getCommentText());
		return new ResponseEntity<Reaction>(reaction,HttpStatus.OK);

	}
    
   


	


}
