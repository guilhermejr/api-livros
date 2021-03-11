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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.form.AutorForm;
import net.guilhermejr.apilivros.service.AutorService;

@Api(tags = "Autor")
@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@ApiOperation(value = "Lista todos os autores")
	@GetMapping
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(this.autorService.listar());
	}
	
	@ApiOperation(value = "Retorna um autor")
	@GetMapping("{id}")
	public ResponseEntity<AutorDTO> autor(@PathVariable Long id) {
		return ResponseEntity.ok(this.autorService.autor(id));
	}
	
	@ApiOperation(value = "Cadastra novo autor")
	@PostMapping()
	public ResponseEntity<AutorDTO> cadastrar(@Valid @RequestBody AutorForm autorForm, UriComponentsBuilder uriBuilder) {
		AutorDTO autorDTO = this.autorService.cadastrar(autorForm);
		URI uri = uriBuilder.path("/autor/{id}").buildAndExpand(autorDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(autorDTO);	
	}

}