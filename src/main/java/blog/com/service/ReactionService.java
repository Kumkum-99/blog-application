package blog.com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blog.com.dto.PostResponse;
import blog.com.model.Post;
import blog.com.model.Reaction;
import blog.com.model.ReactionType;
import blog.com.model.User;
import blog.com.repository.PostRepository;
import blog.com.repository.ReactionRepository;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ReactionService {
	private static final org.slf4j.Logger LOGGER=LoggerFactory.getLogger(ReactionService.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ReactionRepository reactionRepository;
	
	
	
	 public Reaction  addLike(User user,Long postId) {

		  Post post =postRepository.findById(postId)
				  .orElseThrow(()-> new RuntimeException("Post not found"));
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
		   
//		   User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found.."));
		   Post post=postRepository.findById(Postid).orElseThrow(()-> new RuntimeException("Post Not found.."));
		   Reaction reaction=new Reaction();
		   reaction.setUser(user);
		   reaction.setPost(post);
		   reaction.setCommentText(commentText);
		   reaction.setType(ReactionType.COMMENT);
		   return reactionRepository.save(reaction);
		
	  }
	   
	   public Reaction updateComment(Long reactionId,String newCommentText) {

		   Reaction existingReaction=reactionRepository.findById(reactionId).orElseThrow(()-> new RuntimeException("Reaction is not present on these post.."));
		   if(existingReaction.getType() !=ReactionType.COMMENT) {
			   throw new IllegalStateException("Only COMMENT reactions can be updated.");
		   }
		    existingReaction.setCommentText(newCommentText);
		    return reactionRepository.save(existingReaction);
		   
	   }
	   
	   public void deleteComment(Long reactionId,User user) {
		  // Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post Not found.."));
		   Reaction getReaction=reactionRepository.findById(reactionId).orElseThrow(()-> new RuntimeException("Comment is not present on these post  "));
		   
		   if(getReaction.getType() == ReactionType.COMMENT) {
			   reactionRepository.delete(getReaction);
		   }
		   if(!getReaction.getUser().getUserId() .equals(user.getUserId())){
			   LOGGER.warn("This is not your post");
		   }
	   }
	   
	   public  long countLikes(Long postId) {
		   Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post Not found"));
		   return post.getReactions().stream().filter(reaction -> reaction.getType() == ReactionType.COMMENT).count();
		 
		 
	   }
	   public   long countComments(Long postId) {
		   Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post Not found"));
		   return post.getReactions().stream().filter(reaction -> reaction.getType() == ReactionType.COMMENT).count();
		 
		 
	   }
	   public List<PostResponse> getAllPosts() {
		    List<Post> posts = postRepository.findAll(); // reactions are fetched eagerly now

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
