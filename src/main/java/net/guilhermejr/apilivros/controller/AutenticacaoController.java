package net.guilhermejr.apilivros.controller;

import javax.servlet.http.HttpServletRequest;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.exception.Exception401;
import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.dto.TokenDTO;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.form.LoginForm;
import net.guilhermejr.apilivros.service.TokenService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroMediaTypeDTO;

@Tag(name = "Autenticacao", description = "Controller de autenticação")
@RestController
@Slf4j
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Operation(summary = "Efetua login gerando token JWT")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Usuário logado e token gerado"),
		@ApiResponse(responseCode = "400", description = "Erro ao tentar logar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class))),
		@ApiResponse(responseCode = "415", description = "Content-Type não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMediaTypeDTO.class)))
	})
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm loginForm) {

		try {
			
			Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getSenha()));
			Usuario usuario = (Usuario) authenticate.getPrincipal();
	
			TokenDTO tokenDTO = this.tokenService.gerarToken(usuario);
			
			log.info("Usuário: "+ loginForm.getEmail() +" autenticado com sucesso.");
			return ResponseEntity.ok(tokenDTO);
			
		} catch (AuthenticationException e) {
			
			log.error("Usuário: "+ loginForm.getEmail() +" não informou uma chave email:senha válidos, ou está inativo.");
			throw new ExceptionPadrao("E-mail e senha inválidos.");
			
		}
		
	}
	
	@Operation(summary = "Renova o token JWT")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Token renovado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class))),
		@ApiResponse(responseCode = "401", description = "Erro ao tentar logar", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class))),
		@ApiResponse(responseCode = "415", description = "Content-Type não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMediaTypeDTO.class)))
	})
	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refresh(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			throw new Exception401("Token inválido");	
		}
		
		token = token.substring(7, token.length());
		
		String refreshToken = request.getHeader("X-Refresh-Token");
		
		if (refreshToken == null || refreshToken.isEmpty()) {
			throw new Exception401("Token inválido");	
		}
		
		TokenDTO tokenDTO = this.tokenService.refresh(token, refreshToken);
		return ResponseEntity.ok(tokenDTO);
		
	}

}
