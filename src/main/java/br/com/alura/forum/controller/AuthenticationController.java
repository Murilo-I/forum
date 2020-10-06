package br.com.alura.forum.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.TokenService;
import br.com.alura.forum.dto.TokenDto;
import br.com.alura.forum.form.LoginForm;

@RestController
@RequestMapping("/login")
@Profile(value = {"prod", "test"})
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.convert();
		
		try {
			Authentication autentica = manager.authenticate(dadosLogin);
			String token = tokenService.generateToken(autentica);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch(AuthenticationException ex) {
			return ResponseEntity.badRequest().build();
		}
	}
}
