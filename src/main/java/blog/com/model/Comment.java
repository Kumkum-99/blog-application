package blog.com.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="comments")
public class Comment {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long CommentId;
	   
	   @Column(columnDefinition = "TEXT", nullable = false)
	   private String commentText;
	   private LocalDateTime commentedAt;
	  @ManyToOne
	   @JoinColumn(name="post_id")
	   @JsonIgnore
	   private Post post;
	   @ManyToOne
	   @JoinColumn(name = "user_id")
	   @JsonIgnore 
	    private User user;

}
