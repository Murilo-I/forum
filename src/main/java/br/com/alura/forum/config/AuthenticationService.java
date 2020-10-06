package br.com.alura.forum.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.models.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UsuarioRepository ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> u = ur.findByEmail(username);
		
		if(u.isPresent()) 
			return u.get();
		
		throw new UsernameNotFoundException("Usuário ou senha inválidos");
	}

	
}
