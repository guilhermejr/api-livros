package net.guilhermejr.apilivros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guilhermejr.apilivros.model.dto.EstanteDTO;
import net.guilhermejr.apilivros.service.EstanteService;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Estante", description = "Controller de estante")
@RestController
@RequestMapping(path = "/estantes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class EstanteController {
	
	@Autowired
	private EstanteService estanteService;
	
	@Operation(summary = "Lista todos as estantes")
	@ApiResponse(responseCode = "200", description = "Lista de estantes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EstanteDTO.class))))
	@GetMapping
	public ResponseEntity<List<EstanteDTO>> listar() {
		return ResponseEntity.ok(this.estanteService.listar());
	}
	
	@Operation(summary = "Retorna uma estante")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna uma estante"),
		@ApiResponse(responseCode = "404", description = "Estante não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<EstanteDTO> estante(@Parameter(description = "ID da estante", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.estanteService.estante(id));
	}

}
