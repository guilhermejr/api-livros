package net.guilhermejr.apilivros.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.service.TokenService;

@Component
public class Token {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private String email = "falecom@guilhermejr.net";
	private String senha = "123456";
	
	public String gerar() {
		Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, this.senha));	 
		return this.tokenService.gerarToken(authenticate);
	}

}
