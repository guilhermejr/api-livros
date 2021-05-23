package net.guilhermejr.apilivros.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.TokenDTO;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.service.TokenService;

@Component
public class Token {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private String email = "falecom@guilhermejr.net";
	private String senha = "C4m1l4@@";
	
	public String gerar() {
		Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, this.senha));	 
		Usuario usuario = (Usuario) authenticate.getPrincipal();
		TokenDTO tokenDTO = this.tokenService.gerarToken(usuario);
		return tokenDTO.getAccess_token();
	}

}
