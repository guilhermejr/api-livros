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
import net.guilhermejr.apilivros.model.dto.IdiomaDTO;
import net.guilhermejr.apilivros.model.form.IdiomaForm;
import net.guilhermejr.apilivros.service.IdiomaService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroMediaTypeDTO;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Idioma", description = "Controller de idioma")
@RestController
@Slf4j
@RequestMapping("/idiomas")
public class IdiomaController {

	@Autowired
	private IdiomaService idiomaService;
	
	@Operation(summary = "Lista todos os idiomas")
	@ApiResponse(responseCode = "200", description = "Lista de idiomas", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IdiomaDTO.class))))
	@GetMapping
	public ResponseEntity<List<IdiomaDTO>> listar() {
		return ResponseEntity.ok(this.idiomaService.listar());
	}
	
	@Operation(summary = "Retorna um idioma")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um idioma"),
		@ApiResponse(responseCode = "404", description = "Idioma não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<IdiomaDTO> idioma(@Parameter(description = "ID do idioma", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.idiomaService.idioma(id));
	}
	
	@Operation(summary = "Cadastra novo idioma")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Idioma cadastrado"),
		@ApiResponse(responseCode = "400", description = "Erro ao cadastrar idioma", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class))),
		@ApiResponse(responseCode = "415", description = "Content-Type não suportado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMediaTypeDTO.class)))
	})
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IdiomaDTO> cadastrar(@Valid @RequestBody IdiomaForm idiomaForm, UriComponentsBuilder uriBuilder) {
		IdiomaDTO idiomaDTO = this.idiomaService.cadastrar(idiomaForm);
		
		log.info("Idioma: "+ idiomaDTO.getDescricao() +" cadastrado com sucesso.");
		
		URI uri = uriBuilder.path("/idiomas/{id}").buildAndExpand(idiomaDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(idiomaDTO);	
	}

}