package net.guilhermejr.apilivros.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guilhermejr.apilivros.model.dto.EditoraDTO;
import net.guilhermejr.apilivros.model.form.EditoraForm;
import net.guilhermejr.apilivros.service.EditoraService;

@Tag(name = "Editora", description = "Controller de editora")
@RestController
@RequestMapping("/editora")
public class EditoraController {
	
	@Autowired
	private EditoraService editoraService;
	
	@Operation(summary = "Lista todos as editoras")
	@GetMapping
	public ResponseEntity<List<EditoraDTO>> listar() {
		return ResponseEntity.ok(this.editoraService.listar());
	}
	
	@Operation(summary = "Retorna uma editora")
	@GetMapping("{id}")
	public ResponseEntity<EditoraDTO> editora(@PathVariable Long id) {
		return ResponseEntity.ok(this.editoraService.editora(id));
	}
	
	@Operation(summary = "Cadastra nova editora")
	@PostMapping()
	public ResponseEntity<EditoraDTO> cadastrar(@Valid @RequestBody EditoraForm editoraForm, UriComponentsBuilder uriBuilder) {
		EditoraDTO ediotraDTO = this.editoraService.cadastrar(editoraForm);
		URI uri = uriBuilder.path("/editora/{id}").buildAndExpand(ediotraDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(ediotraDTO);	
	}

}
