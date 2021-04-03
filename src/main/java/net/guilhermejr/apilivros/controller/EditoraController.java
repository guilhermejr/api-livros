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
import net.guilhermejr.apilivros.model.dto.EditoraDTO;
import net.guilhermejr.apilivros.model.form.EditoraForm;
import net.guilhermejr.apilivros.service.EditoraService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Editora", description = "Controller de editora")
@RestController
@RequestMapping(path = "/editora", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class EditoraController {
	
	@Autowired
	private EditoraService editoraService;
	
	@Operation(summary = "Lista todos as editoras")
	@ApiResponse(responseCode = "200", description = "Lista de editoras", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EditoraDTO.class))))
	@GetMapping
	public ResponseEntity<List<EditoraDTO>> listar() {
		return ResponseEntity.ok(this.editoraService.listar());
	}
	
	@Operation(summary = "Retorna uma editora")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna uma editora"),
		@ApiResponse(responseCode = "404", description = "Editora não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<EditoraDTO> editora(@Parameter(description = "ID da editora", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.editoraService.editora(id));
	}
	
	@Operation(summary = "Cadastra nova editora")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Editora cadastrada"),
		@ApiResponse(responseCode = "400", description = "Erro ao cadastrar editora", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class)))
	})
	@PostMapping()
	public ResponseEntity<EditoraDTO> cadastrar(@Valid @RequestBody EditoraForm editoraForm, UriComponentsBuilder uriBuilder) {
		EditoraDTO ediotraDTO = this.editoraService.cadastrar(editoraForm);
		URI uri = uriBuilder.path("/editora/{id}").buildAndExpand(ediotraDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(ediotraDTO);	
	}

}
