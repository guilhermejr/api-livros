package net.guilhermejr.apilivros.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.dto.TokenDTO;
import net.guilhermejr.apilivros.model.form.LoginForm;
import net.guilhermejr.apilivros.service.TokenService;

@Tag(name = "Autenticacao", description = "Controller de autenticacao")
@RestController
@Slf4j
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm loginForm) {

		try {
			
			Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getSenha()));
			String token = this.tokenService.gerarToken(authenticate);
			TokenDTO tokenDTO = TokenDTO.builder().access_token(token).token_type("Bearer").build();
			log.info("Usuário: "+ loginForm.getEmail() +" autenticado com sucesso.");
			return ResponseEntity.ok(tokenDTO);
			
		} catch (AuthenticationException e) {
			
			log.error("Usuário: "+ loginForm.getEmail() +" não informou uma chave email:senha válidos.");
			throw new ExceptionPadrao("E-mail e senha inválidos.");
			
		}
		
	}

}
