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
import net.guilhermejr.apilivros.model.dto.TipoDTO;
import net.guilhermejr.apilivros.model.form.TipoForm;
import net.guilhermejr.apilivros.service.TipoService;

@Api(tags = "Tipo")
@RestController
@RequestMapping("/tipo")
public class TipoController {

	@Autowired
	private TipoService tipoService;
	
	@ApiOperation(value = "Lista todos os tipos")
	@GetMapping
	public ResponseEntity<List<TipoDTO>> listar() {
		return ResponseEntity.ok(this.tipoService.listar());
	}
	
	@ApiOperation(value = "Retorna um tipo")
	@GetMapping("{id}")
	public ResponseEntity<TipoDTO> tipo(@PathVariable Long id) {
		return ResponseEntity.ok(this.tipoService.tipo(id));
	}
	
	@ApiOperation(value = "Cadastra novo tipo")
	@PostMapping()
	public ResponseEntity<TipoDTO> cadastrar(@Valid @RequestBody TipoForm tipoForm, UriComponentsBuilder uriBuilder) {
		TipoDTO tipoDTO = this.tipoService.cadastrar(tipoForm);
		URI uri = uriBuilder.path("/tipo/{id}").buildAndExpand(tipoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(tipoDTO);	
	}

}