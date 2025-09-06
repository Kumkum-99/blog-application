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

import blog.com.dto.LoginRequestDto;
import blog.com.dto.PostResponse;
import blog.com.dto.Userdto;
import blog.com.model.User;
import blog.com.service.CustomeUserDetailsService;
import blog.com.service.ReactionService;
import blog.com.service.TokenBlacklistService;
import blog.com.service.UserService;
import blog.com.utilis.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/public/api")
@Tag(name="AUTH API")

public class AuthController {
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenBlacklistService blacklistService;
	@Autowired
	private ReactionService reactionService;
	
	@GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(reactionService.getAllPosts());
    }
	
	

	@PostMapping("/registration")
	public ResponseEntity<?> register(@Valid @RequestBody Userdto user){
	    User savedUser = userService.save(user);
	    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
	            "message", "User registered successfully",
	            "email", savedUser.getEmail()
	        ));
	}

	@PostMapping("/login")
	public ResponseEntity<?>login( @RequestBody LoginRequestDto user){
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
		    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	        		.body(Map.of("error","Invalid credentials"));
			
	        
		}
	}
	
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		String authHeader=request.getHeader("Authorization");
		if(authHeader !=null && authHeader.startsWith("Bearer ")) {
			String  token=authHeader.substring(7);
		     java.util.Date expiryDate=jwtUtils.extractExiprationDate(token);
		     blacklistService.blacklistToken(token, expiryDate.getTime());
		     return ResponseEntity.ok("Logged out successfully");
		
		}
		return ResponseEntity.ok("No token found");
	}
	
	
	

	
	


}
