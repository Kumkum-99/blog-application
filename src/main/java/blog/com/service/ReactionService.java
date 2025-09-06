package blog.com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import blog.com.dto.PostResponse;
import blog.com.exception.ResourceNotFound;
import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.ReactionType;
import blog.com.model.User;
import blog.com.repository.PostRepository;
import blog.com.repository.ReactionRepository;



@Service

public class ReactionService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ReactionRepository reactionRepository;
	
	
	
	 public Reaction  addLike(User user,Long postId) {

		  Post post =postRepository.findById(postId)
				  .orElseThrow(()-> new ResourceNotFound("Post not found:"+postId));
		  Optional<Reaction> existingLike = reactionRepository.findByUserAndPostAndType(user, post, ReactionType.LIKE);
		  if(existingLike.isPresent()) {
			  reactionRepository.delete(existingLike.get());
			  return null;
		  }else { 
		  Reaction reaction = new Reaction();
		
	      reaction.setUser(user);
	      reaction.setPost(post);
	      reaction.setType(ReactionType.LIKE);
	      return reactionRepository.save(reaction);	
		  }
	  }

	  
	  
	  
	   public Reaction addComment(User user,Long Postid,String commentText) {
		   
		   Post post=postRepository.findById(Postid).orElseThrow(()-> new ResourceNotFound("Post Not found.."+ Postid));
		   Reaction reaction=new Reaction();
		   reaction.setUser(user);
		   reaction.setPost(post);
		   reaction.setCommentText(commentText);
		   reaction.setType(ReactionType.COMMENT);
		   
		   return reactionRepository.save(reaction);
		
	  }
	   
	   public Reaction updateComment(Long reactionId,String newCommentText) {

		   Reaction existingReaction=reactionRepository.findById(reactionId).orElseThrow(()-> new ResourceNotFound("Reaction is not present on these post.."));
		   if(existingReaction.getType() !=ReactionType.COMMENT) {
			   throw new ResourceNotFound("Only COMMENT reactions can be updated.");
		   }
		    existingReaction.setCommentText(newCommentText);
		    return reactionRepository.save(existingReaction);
		   
	   }
	   
	   public void deleteComment(Long reactionId,User user) {
		
		   Reaction getReaction=reactionRepository.findById(reactionId).orElseThrow(()-> new ResourceNotFound("Comment is not present on these post  "));
		   if(!getReaction.getUser().getUserId() .equals(user.getUserId())){
			
		   }
		   if(getReaction.getType() == ReactionType.COMMENT) {
			   reactionRepository.delete(getReaction);
		   }
	   }
	   
	   public  long countLikes(Long postId) {
		   Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound("Post Not found"));
		   return post.getReactions().stream().filter(reaction -> reaction.getType() == ReactionType.LIKE).count();
		 
		 
	   }
	   public   long countComments(Long postId) {
		   Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound("Post Not found"));
		   return post.getReactions().stream().filter(reaction -> reaction.getType() == ReactionType.COMMENT).count();
		 
		 
	   }
	   public List<PostResponse> getAllPosts() {
		    List<Post> posts = postRepository.findAll(); 

		    return posts.stream().map(post -> {
		        long likes = post.getReactions().stream()
		                .filter(r -> r.getType() == ReactionType.LIKE)
		                .count();

		        long comments = post.getReactions().stream()
		                .filter(r -> r.getType() == ReactionType.COMMENT)
		                .count();

		        return new PostResponse(post.getPostId(), post.getTitle(), post.getContent(), likes, comments);
		    }).collect(Collectors.toList());
		}

	   
	 
	   
	   
	   
	   
	  
	   

}
