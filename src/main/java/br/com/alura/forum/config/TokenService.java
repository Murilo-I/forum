package br.com.alura.forum.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.models.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		Usuario user = (Usuario) authentication.getPrincipal();
		Date date = new Date();
		Date exp = new Date(date.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder().setIssuer("API fórum Malura").setSubject(user.getId().toString())
				.setIssuedAt(date).setExpiration(exp).signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Long getUserById(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
	    return Long.parseLong(body.getSubject());
	}

}
