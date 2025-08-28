package blog.com.service;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import blog.com.model.User;
import blog.com.repository.UserRepository;

@Component
public class CustomeUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email);
		 if (user == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
		 String roleWithPrefix="ROLE_"+user.getRole().name();
		 return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix))
	        );
      
	}

}
