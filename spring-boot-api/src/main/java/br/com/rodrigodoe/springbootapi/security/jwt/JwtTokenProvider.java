package br.com.rodrigodoe.springbootapi.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.rodrigodoe.springbootapi.exception.InvalidAuthenticationException;
import br.com.rodrigodoe.springbootapi.services.UserServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret.key:secret}")
	private String secretKey = "secret";
	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInnMilliseconds = 3600000;

	@Autowired
	private UserServices userServices;

	@PostConstruct
	public void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInnMilliseconds);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails user = this.userServices.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(user,"", user.getAuthorities());
		
	}
	
	private String getUsername(String token) { 
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	public boolean validateToken(String token) { 
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			if(claims.getBody().getExpiration().before(new Date())) { 
				return false;
			}
			return true;
		}catch(Exception e ) {
			throw new InvalidAuthenticationException("Expired or invalid token");
		}
	}

}