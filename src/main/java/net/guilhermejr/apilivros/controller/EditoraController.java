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

import net.guilhermejr.apilivros.model.dto.EditoraDTO;
import net.guilhermejr.apilivros.model.form.EditoraForm;
import net.guilhermejr.apilivros.service.EditoraService;

@RestController
@RequestMapping("/editora")
public class EditoraController {
	
	@Autowired
	private EditoraService editoraService;
	
	@GetMapping
	public ResponseEntity<List<EditoraDTO>> listar() {
		return ResponseEntity.ok(this.editoraService.listar());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EditoraDTO> editora(@PathVariable Long id) {
		return ResponseEntity.ok(this.editoraService.editora(id));
	}
	
	@PostMapping()
	public ResponseEntity<EditoraDTO> cadastrar(@Valid @RequestBody EditoraForm editoraForm, UriComponentsBuilder uriBuilder) {
		EditoraDTO ediotraDTO = this.editoraService.cadastrar(editoraForm);
		URI uri = uriBuilder.path("/editora/{id}").buildAndExpand(ediotraDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(ediotraDTO);	
	}

}
