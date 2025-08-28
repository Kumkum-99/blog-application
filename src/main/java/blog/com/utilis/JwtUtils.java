package  blog.com.utilis;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtUtils {
	@Value("${jwt.secret}")
	private String secretKey;
	public String generateToken(String email,String role) {
		HashMap<String, Object>claims=new HashMap<String, Object>();
		claims.put("role", "ROLE_"+role.toUpperCase());
		return createToken(claims, email);
	}
	public Date extractExiprationDate(String token) {
		return extractAllClaims(token).getExpiration();
	}
	public Boolean isTokenExpired(String token) {
		   return extractExiprationDate(token).before(new Date());
	}
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public String createToken(HashMap<String, Object> claims,String subject) {
		return Jwts
				.builder().claims(claims)
				.subject(subject)
				.header().empty()
                .add("Type","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(getSigningKey()).compact();
                
                }
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	private Claims extractAllClaims(String token) {
		
		return Jwts.parser().verifyWith((SecretKey)getSigningKey()).build().
		parseSignedClaims(token).getPayload();
	}
	public Boolean validateToken(String token) {
		try {
	        Claims claims = extractAllClaims(token);
	        return !isTokenExpired(token);
	    } catch (Exception e) {
	        return false;
	    }
	   
	}
	
	
}