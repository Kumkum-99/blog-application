package blog.com.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import blog.com.service.CustomeUserDetailsService;
import blog.com.utilis.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private CustomeUserDetailsService customeUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		String authHeader=request.getHeader("Authorization");
		String email=null;
		String jwt=null;
//		if(authHeader !=null && authHeader.startsWith("Bearer ")) {
//			jwt=authHeader.substring(7);
//			email=jwtUtils.extractEmail(jwt);
//			
//		}
		 if (authHeader != null && authHeader.startsWith("Bearer ")) {
		        jwt = authHeader.substring(7);
		        try {
		            email = jwtUtils.extractEmail(jwt);
		        } catch (Exception e) {
		            // Invalid token, just continue (don’t block)
		            filterChain.doFilter(request, response);
		            return;
		        }
		    }
		if(email!=null) {
			UserDetails userDetails=customeUserDetailsService.loadUserByUsername(email);
			if(jwtUtils.validateToken(jwt)) {
				UsernamePasswordAuthenticationToken auth=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				 auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
				
		 filterChain.doFilter(request, response);
	}
	  @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) {
	        String path = request.getServletPath();
	        return path.startsWith("/public/"); // 🚀 Skip JWT for public APIs
	    }
	

}
