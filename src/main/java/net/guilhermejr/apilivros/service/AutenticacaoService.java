package net.guilhermejr.apilivros.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		
		Optional<Usuario> usuarioRetorno = this.usuarioRepository.findByEmailAndAtivo(email, Boolean.TRUE);
		if (usuarioRetorno.isPresent()) {
			Usuario usuario = usuarioRetorno.get();
			usuario.setUltimoAcesso(LocalDateTime.now());
			this.usuarioRepository.save(usuario);
			return usuario;
		}
		
		throw new ExceptionPadrao("E-mail e senha inválidos.");
		
	}

}
