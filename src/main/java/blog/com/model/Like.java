package blog.com.model;

import java.time.LocalDateTime;
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
@Table(name="likes")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long likeId;
	
	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false)
	 private User user;
	 
	  @ManyToOne
	  @JoinColumn(name = "post_id", nullable = false)
	  private Post post;

	   private LocalDateTime createdAt = LocalDateTime.now();

}
