package net.guilhermejr.apilivros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.form.TrocaSenhaForm;
import net.guilhermejr.apilivros.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void trocarSenha(TrocaSenhaForm trocaSenhaForm, Usuario usuario) {
		
		if (!trocaSenhaForm.getSenhaNova().equals(trocaSenhaForm.getSenhaNovaConfirmar())) {
			throw new ExceptionPadrao("Nova senha e Confirmar nova senha devem ser iguais");
		}
		
		Usuario u = this.usuarioRepository.getOne(usuario.getId());
		
		if (!new BCryptPasswordEncoder().matches(trocaSenhaForm.getSenhaAtual(), u.getSenha())) {
			throw new ExceptionPadrao("Senha atual está errada.");
		}
		
		u.setSenha(new BCryptPasswordEncoder().encode(trocaSenhaForm.getSenhaNova()));
		this.usuarioRepository.save(u);
		
	}

}
