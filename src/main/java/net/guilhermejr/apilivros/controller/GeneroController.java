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
import net.guilhermejr.apilivros.model.dto.GeneroDTO;
import net.guilhermejr.apilivros.model.form.GeneroForm;
import net.guilhermejr.apilivros.service.GeneroService;

@Api(tags = "Gênero")
@RestController
@RequestMapping("/genero")
public class GeneroController {

	@Autowired
	private GeneroService generoService;
	
	@ApiOperation(value = "Lista todos os gêneros")
	@GetMapping
	public ResponseEntity<List<GeneroDTO>> listar() {
		return ResponseEntity.ok(this.generoService.listar());
	}
	
	@ApiOperation(value = "Retorna um gênero")
	@GetMapping("{id}")
	public ResponseEntity<GeneroDTO> genero(@PathVariable Long id) {
		return ResponseEntity.ok(this.generoService.genero(id));
	}
	
	@ApiOperation(value = "Cadastra novo gênero")
	@PostMapping()
	public ResponseEntity<GeneroDTO> cadastrar(@Valid @RequestBody GeneroForm generoForm, UriComponentsBuilder uriBuilder) {
		GeneroDTO generoDTO = this.generoService.cadastrar(generoForm);
		URI uri = uriBuilder.path("/genero/{id}").buildAndExpand(generoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(generoDTO);	
	}

}