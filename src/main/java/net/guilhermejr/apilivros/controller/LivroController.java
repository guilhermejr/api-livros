package net.guilhermejr.apilivros.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.guilhermejr.apilivros.model.dto.LivroDTO;
import net.guilhermejr.apilivros.model.form.LivroForm;
import net.guilhermejr.apilivros.service.LivroService;

@Tag(name = "Livro", description = "Controller de livro")
@RestController
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private LivroService livroService;

	@Operation(summary = "Lista livros com filtro")
	@GetMapping
	public ResponseEntity<Page<LivroDTO>> listar(
			@RequestParam(required = false) Long estante,
			@RequestParam(required = false) String titulo,
			@PageableDefault(page = 0, size = 24, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {

		return ResponseEntity.ok(this.livroService.listar(estante, titulo, paginacao));

	}

	@Operation(summary = "Pesquisa livros")
	@GetMapping("/pesquisar")
	public ResponseEntity<Page<LivroDTO>> pesquisar(@RequestParam(required = false) Long estante,
			@RequestParam(required = false) String titulo, 
			@RequestParam(required = false) String isbn,
			@RequestParam(required = false) Long editora, 
			@RequestParam(required = false) Long autor,
			@RequestParam(required = false) Long genero,
			@RequestParam(required = false) Long idioma,
			@RequestParam(required = false) Long tipo,
			@RequestParam(required = false) Integer ano,
			@PageableDefault(page = 0, size = 24, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {

		return ResponseEntity.ok(this.livroService.pesquisar(estante, titulo, isbn, editora, autor, genero, idioma, tipo, ano, paginacao));

	}

	@Operation(summary = "Retorna um livro")
	@GetMapping("{id}")
	public ResponseEntity<LivroDTO> livro(@PathVariable Long id) {
		return ResponseEntity.ok(this.livroService.livro(id));
	}

	@Operation(summary = "Cadastra novo livro")
	@PostMapping()
	public ResponseEntity<LivroDTO> cadastrar(@RequestBody @Valid LivroForm livroForm,
			UriComponentsBuilder uriBuilder) {

		LivroDTO livroDTO = this.livroService.cadastrar(livroForm);

		URI uri = uriBuilder.path("/livro/{id}").buildAndExpand(livroDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(livroDTO);
	}

}
