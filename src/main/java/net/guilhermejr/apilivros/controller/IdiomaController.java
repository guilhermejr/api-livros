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
import net.guilhermejr.apilivros.model.dto.IdiomaDTO;
import net.guilhermejr.apilivros.model.form.IdiomaForm;
import net.guilhermejr.apilivros.service.IdiomaService;

@Tag(name = "Idioma", description = "Controller de idioma")
@RestController
@RequestMapping("/idioma")
public class IdiomaController {

	@Autowired
	private IdiomaService idiomaService;
	
	@Operation(summary = "Lista todos os idiomas")
	@GetMapping
	public ResponseEntity<List<IdiomaDTO>> listar() {
		return ResponseEntity.ok(this.idiomaService.listar());
	}
	
	@Operation(summary = "Retorna um idioma")
	@GetMapping("{id}")
	public ResponseEntity<IdiomaDTO> idioma(@PathVariable Long id) {
		return ResponseEntity.ok(this.idiomaService.idioma(id));
	}
	
	@Operation(summary = "Cadastra novo idioma")
	@PostMapping()
	public ResponseEntity<IdiomaDTO> cadastrar(@Valid @RequestBody IdiomaForm idiomaForm, UriComponentsBuilder uriBuilder) {
		IdiomaDTO idiomaDTO = this.idiomaService.cadastrar(idiomaForm);
		URI uri = uriBuilder.path("/idioma/{id}").buildAndExpand(idiomaDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(idiomaDTO);	
	}

}