package blog.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponseDto {
	private Long reactionId;
    private String commentText;
    private String type;   // COMMENT, LIKE, etc.

    private Long userId;
    private String userName;

    private Long postId;
    private String postTitle;

}
