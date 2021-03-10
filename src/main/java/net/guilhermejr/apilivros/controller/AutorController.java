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

import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.form.AutorForm;
import net.guilhermejr.apilivros.service.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@GetMapping
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(this.autorService.listar());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AutorDTO> autor(@PathVariable Long id) {
		return ResponseEntity.ok(this.autorService.autor(id));
	}
	
	@PostMapping()
	public ResponseEntity<AutorDTO> cadastrar(@Valid @RequestBody AutorForm autorForm, UriComponentsBuilder uriBuilder) {
		AutorDTO autorDTO = this.autorService.cadastrar(autorForm);
		URI uri = uriBuilder.path("/autor/{id}").buildAndExpand(autorDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(autorDTO);	
	}

}