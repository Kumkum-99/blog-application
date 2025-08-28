package blog.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.dto.PostResponse;
import blog.com.model.Post;
import blog.com.model.User;
import blog.com.service.CustomeUserDetailsService;
import blog.com.service.ReactionService;
import blog.com.service.UserService;
import blog.com.utilis.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/public/api/auth")
@Slf4j
public class AuthController {
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;
	
	@Autowired
	private ReactionService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReactionService reactionService;
//	@PostMapping("/registration")
//	public ResponseEntity<User> register(@RequestBody User user){
//	User saveUser=	userService.save(user);
//		return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
//	}
	@PostMapping("/registration")
	public ResponseEntity<?> register(@Valid @RequestBody User user){
	    User savedUser = userService.save(user);

	    // Log the saved user
	    log.info("New user registered: {}", savedUser.getEmail());

	    // Return the saved user with CREATED status
	    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
	            "message", "User registered successfully",
	            "email", savedUser.getEmail()
	        ));
	}

	@PostMapping("/login")
	public ResponseEntity<?>login( @RequestBody User user){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			UserDetails userDetails=customeUserDetailsService.loadUserByUsername(user.getEmail());
			 User userDb=userService.findByEmail(user.getEmail());
			 String jwt = jwtUtils.generateToken(userDetails.getUsername(),userDb.getRole().name()); 
			  return ResponseEntity.ok(Map.of(
					  "token",jwt,
					  "type","Bearer",
					  "role",userDb.getRole().name()
					  ));
			
		}catch(Exception e) {
			log.error("Exception occurred during login", e);
	        return 
	        		ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	        		.body(Map.of("error","Invalid credentials"));
			
	        
		}
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<PostResponse>> getpost(){
		return new ResponseEntity<>(service.getAllPosts(),HttpStatus.OK);
		
	}
	
	
	/* after comment*/
//	 @GetMapping("/{postId}/likes/count")
//	    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
//	        long likeCount = reactionService.countLikes(postId);
//	        return ResponseEntity.ok(likeCount);
//	    }

}
