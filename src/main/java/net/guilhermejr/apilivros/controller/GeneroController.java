package net.guilhermejr.apilivros.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.model.dto.GeneroDTO;
import net.guilhermejr.apilivros.model.form.GeneroForm;
import net.guilhermejr.apilivros.service.GeneroService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Gênero", description = "Controller de gênero")
@RestController
@Slf4j
@RequestMapping(path = "/genero", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class GeneroController {

	@Autowired
	private GeneroService generoService;
	
	@Operation(summary = "Lista todos os gêneros")
	@ApiResponse(responseCode = "200", description = "Lista de gêneros", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GeneroDTO.class))))
	@GetMapping
	public ResponseEntity<List<GeneroDTO>> listar() {
		return ResponseEntity.ok(this.generoService.listar());
	}
	
	@Operation(summary = "Retorna um gênero")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um gênero"),
		@ApiResponse(responseCode = "404", description = "Gênero não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<GeneroDTO> genero(@Parameter(description = "ID do gênero", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.generoService.genero(id));
	}
	
	@Operation(summary = "Cadastra novo gênero")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Gênero cadastrado"),
		@ApiResponse(responseCode = "400", description = "Erro ao cadastrar gênero", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class)))
	})
	@PostMapping()
	public ResponseEntity<GeneroDTO> cadastrar(@Valid @RequestBody GeneroForm generoForm, UriComponentsBuilder uriBuilder) {
		GeneroDTO generoDTO = this.generoService.cadastrar(generoForm);
		
		log.info("Gênero: "+ generoDTO.getDescricao() +" cadastrado com sucesso.");
		
		URI uri = uriBuilder.path("/genero/{id}").buildAndExpand(generoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(generoDTO);	
	}

}