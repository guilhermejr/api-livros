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
import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.form.AutorForm;
import net.guilhermejr.apilivros.service.AutorService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroMediaTypeDTO;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Autor", description = "Controller de autor")
@RestController
@Slf4j
@RequestMapping("/autores")
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@Operation(summary = "Lista todos os autores")
	@ApiResponse(responseCode = "200", description = "Lista de autores", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AutorDTO.class))))
	@GetMapping()
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(this.autorService.listar());
	}
	
	@Operation(summary = "Retorna um autor")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um autor"),
		@ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<AutorDTO> autor(@Parameter(description = "ID do autor", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.autorService.autor(id));
	}
	
	@Operation(summary = "Cadastra novo autor")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Autor cadastrado"),
		@ApiResponse(responseCode = "400", description = "Erro ao cadastrar autor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class))),
		@ApiResponse(responseCode = "415", description = "Content-Type não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMediaTypeDTO.class)))
	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AutorDTO> cadastrar(@Valid @RequestBody AutorForm autorForm, UriComponentsBuilder uriBuilder) {
		AutorDTO autorDTO = this.autorService.cadastrar(autorForm);
		
		log.info("Autor: "+ autorDTO.getDescricao() +" cadastrado com sucesso.");
		
		URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(autorDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(autorDTO);	
	}

}