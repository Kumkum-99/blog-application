package blog.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.model.Post;
import blog.com.model.User;
import blog.com.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	
	@Autowired
	private UserService userService;
//	@PostMapping("/registration")
//	public ResponseEntity<User> register(@RequestBody User user){
//	User saveUser=	userService.save(user);
//		return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
//	}
	@GetMapping("/allpost")
	public List<Post> getALlPost(){
		return  userService.getAllPost();
	}
	
	//counting counting total likes of user 
	//fetch the all the post of user
	
	
	
	


}
