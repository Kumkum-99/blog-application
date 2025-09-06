package blog.com.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistService {
   private final ConcurrentHashMap<String, Long> blacklist=
new ConcurrentHashMap<>();
   
public void blacklistToken(String token,long expiryTime) {
	blacklist.put(token, expiryTime);
}

public boolean isTokenBlacklisted(String token) {
	if(!blacklist.contains(token)) return false;
	long expiry=blacklist.get(token);
	if(System.currentTimeMillis() > expiry) {
		blacklist.remove(token);
		return false;
	}
	return true;
}
}
