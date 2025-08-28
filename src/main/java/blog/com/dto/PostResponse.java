package blog.com.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostResponse {
	  public PostResponse(Long postId, String title, String content, long likeCount, long commentCount) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}
	private Long postId;
	    private String title;
	    private String content;
	    private long likeCount;
	    private long commentCount;

}
