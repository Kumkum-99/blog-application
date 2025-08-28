package blog.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import blog.com.model.Post;
import blog.com.repository.PostRepository;

@Component
public class PostResponseService {
	
	@Autowired
	private PostRepository postRepository;
	
	public List<Post> getAllpost(){
	return null;
	
	}

}
