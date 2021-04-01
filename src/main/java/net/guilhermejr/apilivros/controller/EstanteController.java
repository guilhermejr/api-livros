package net.guilhermejr.apilivros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guilhermejr.apilivros.model.dto.EstanteDTO;
import net.guilhermejr.apilivros.service.EstanteService;

@Tag(name = "Estante", description = "Controller de estante")
@RestController
@RequestMapping("/estante")
public class EstanteController {
	
	@Autowired
	private EstanteService estanteService;
	
	@Operation(summary = "Lista todos as estantes")
	@GetMapping
	public ResponseEntity<List<EstanteDTO>> listar() {
		return ResponseEntity.ok(this.estanteService.listar());
	}
	
	@Operation(summary = "Retorna uma estante")
	@GetMapping("{id}")
	public ResponseEntity<EstanteDTO> estante(@PathVariable Long id) {
		return ResponseEntity.ok(this.estanteService.estante(id));
	}

}
