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
import net.guilhermejr.apilivros.model.dto.TipoDTO;
import net.guilhermejr.apilivros.service.TipoService;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Tipo", description = "Controller de tipo")
@RestController
@RequestMapping(path = "/tipos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TipoController {

	@Autowired
	private TipoService tipoService;
	
	@Operation(summary = "Lista todos os tipos")
	@ApiResponse(responseCode = "200", description = "Lista de tipos", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TipoDTO.class))))
	@GetMapping
	public ResponseEntity<List<TipoDTO>> listar() {
		return ResponseEntity.ok(this.tipoService.listar());
	}
	
	@Operation(summary = "Retorna um tipo")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um tipo"),
		@ApiResponse(responseCode = "404", description = "Tipo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<TipoDTO> tipo(@Parameter(description = "ID do tipo", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.tipoService.tipo(id));
	}

}