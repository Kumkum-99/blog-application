package blog.com.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Post")
public class Post {
	   public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<Reaction> getReactions() {
	    return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
	    this.reactions = reactions;
	}

	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long postId;
	   private String title;
	   @Column( columnDefinition = "TEXT")
	   private String content;
	   private LocalDateTime createAt;
	   private LocalDateTime updatedAt;
	   @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	   @JsonIgnore
	   
	   private List<Reaction> reactions = new ArrayList<>();
//	   @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//	   private Comment comment;
//	   
//	   
//	   public Comment getComment() {
//		return comment;
//	}
//	public void setComment(Comment comment) {
//		this.comment = comment;
//	}

	@ManyToOne
	   @JoinColumn(name="author_id")
	   @JsonIgnore
	   private User author;
	   
	   

}
