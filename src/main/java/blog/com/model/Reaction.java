package blog.com.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Reaction")
public class Reaction {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long reactionId;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
//
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "post_id", nullable = false)
	    private Post post;

	    private String commentText;

	    @Enumerated(EnumType.STRING)
	    private ReactionType type;
    
    
    

}
