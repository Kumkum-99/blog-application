package blog.com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import blog.com.dto.Userdto;
import blog.com.model.Post;
import blog.com.model.User;
import blog.com.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="ADMIN API")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	
	@GetMapping("/search")
	public ResponseEntity<Page<Post>> searchPosts(
	        @RequestParam(value = "keyword", required = false) String keyword,
	        @PageableDefault(size=10, sort="createAt", direction = Sort.Direction.DESC) Pageable pageable) {
	    
	    Page<Post> posts = adminService.searchPosts(keyword, pageable);
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	
	@GetMapping("/get_all_posts/{userId}")
	public ResponseEntity<List<Post>> getAllThePost(@PathVariable("userId") long userId){
		List<Post> posts=adminService.getPosts(userId);
		return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete_comment/{reactionId}")
	public ResponseEntity<String> deleteComment(@PathVariable("reactionId") long reactionId){
		adminService.deleteComment(reactionId);
		return ResponseEntity.ok("Comment deleted successfully..");
		
	}
	
	 @PostMapping("/addUser")
	    public ResponseEntity<?> addUser( @Valid @RequestBody Userdto userdto) {
	        // your service logic here
		    System.out.println("DTO received: " + userdto);
		 User user=adminService.addUser(userdto);
	        return new ResponseEntity<>(user,HttpStatus.CREATED);
	    }

	

}
