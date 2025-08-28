package blog.com.utilis;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtilis {
  
	
	public String currentUser() {
		var authentication=SecurityContextHolder.getContext().getAuthentication();
		if(authentication ==null || !authentication.isAuthenticated()) {
			return null;
		}
		return authentication.getName();
	}
	
	public Boolean  isAdmin() {
		var auth=SecurityContextHolder.getContext().getAuthentication();
		return 
				auth.getAuthorities()
				.stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		
	}
}
