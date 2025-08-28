package blog.com.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.model.Reaction;
import blog.com.model.User;
import blog.com.service.ReactionService;
import blog.com.service.UserService;
import blog.com.utilis.AuthUtilis;

@RestController
@RequestMapping("/api/posts")
@PreAuthorize("hasRole('USER')")
public class ReactionController {
	
	@Autowired
	private ReactionService reactionService;
	
	@Autowired
	private AuthUtilis authUtilis;
	
	@Autowired
	private UserService userService;
	
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
	
	
	@PostMapping("/{postId}/likes")
	public ResponseEntity<String> toggleLike(
	        @PathVariable("postId") Long postId
	       ){
	    
	    Reaction result = reactionService.addLike(currentUser, postId);
	    
	    if (result == null) {
	        return ResponseEntity.ok("Like removed");
	    } else {
	        return ResponseEntity.ok("Like added");
	    }
	}
	
	
	@PostMapping("/{postId}/comments")
	public ResponseEntity<Reaction> newComment(@PathVariable("postId") Long postId, @RequestBody Reaction requestReaction){
		Reaction reaction=reactionService.addComment(currentUser, postId, requestReaction.getCommentText());
		return new ResponseEntity<Reaction>(reaction,HttpStatus.OK);

  
	}
	@DeleteMapping("/delete/comment/{reactionId}")
	public ResponseEntity<?> deletePostById(@PathVariable("reactionId") Long reactionId){
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
