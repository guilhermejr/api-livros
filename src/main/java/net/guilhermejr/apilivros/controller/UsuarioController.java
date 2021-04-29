package net.guilhermejr.apilivros.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.form.TrocaSenhaForm;
import net.guilhermejr.apilivros.service.UsuarioService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroMediaTypeDTO;

@Tag(name = "Usuário", description = "Controller de usuário")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary = "Troca senha do usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Senha trocada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Erro ao trocar senha do usuário", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class))),
		@ApiResponse(responseCode = "415", description = "Content-Type não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMediaTypeDTO.class)))
	})
	@PutMapping(path = "trocar-senha", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void trocarSenha(@Valid @RequestBody TrocaSenhaForm trocaSenhaForm, @AuthenticationPrincipal Usuario usuario) {
		
		this.usuarioService.trocarSenha(trocaSenhaForm, usuario);
		
	}

}
