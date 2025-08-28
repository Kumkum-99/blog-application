package blog.com.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
@Entity
@Table(name="Reactions")
public class Reaction {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long reactionId;
	   public Long getReactionId() {
		return reactionId;
	}
	public void setReactionId(Long reactionId) {
		this.reactionId = reactionId;
	}
	public ReactionType getType() {
		return type;
	}
	public void setType(ReactionType type) {
		this.type = type;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getSharePlatform() {
		return sharePlatform;
	}
	public void setSharePlatform(String sharePlatform) {
		this.sharePlatform = sharePlatform;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Enumerated(EnumType.STRING)
	   private ReactionType type;
	   @Column(nullable = true)
	   private String commentText;
	   @Column(nullable = true)
	   private String sharePlatform;
	   @ManyToOne
	   @JoinColumn(name="post_id")
	   @JsonIgnore
	   private Post post;
	   @ManyToOne
	   @JoinColumn(name = "user_id")
	   @JsonIgnore 
	    private User user;

	   
	    

}
