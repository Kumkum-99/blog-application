package blog.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import blog.com.filter.JwtFilter;
import blog.com.service.CustomeUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  
public class SecurityConfig {
  @Autowired
  private CustomeUserDetailsService customeUserDetailsService;
  
  @Autowired
  private JwtFilter jwtFilter;
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  return http.authorizeHttpRequests(
			  request -> 
			  request.requestMatchers("/public/api/**")
			  .permitAll()
			  .requestMatchers("/admin/**").hasRole("ADMIN")
			  .requestMatchers("/api/**").hasRole("USER")
			  .anyRequest().authenticated()) .cors().and()
	          .csrf(AbstractHttpConfigurer::disable).
	          addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	          .build();         
  }
  
  @Bean
  public AuthenticationManager authenticationManger(HttpSecurity http)throws Exception {
	  return http.getSharedObject(AuthenticationManagerBuilder.class)
			  .userDetailsService(customeUserDetailsService)
			  .passwordEncoder(passwordEncoder())
			  .and()
			  .build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
  }
 
}
