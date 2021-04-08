package net.guilhermejr.apilivros.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.model.dto.LivroDTO;
import net.guilhermejr.apilivros.model.dto.LivrosDTO;
import net.guilhermejr.apilivros.model.form.LivroForm;
import net.guilhermejr.apilivros.service.LivroService;
import net.guilhermejr.apilivros.validacao.ErroDeFormularioDTO;
import net.guilhermejr.apilivros.validacao.ErroPadraoDTO;

@Tag(name = "Livro", description = "Controller de livro")
@RestController
@Slf4j
@RequestMapping(path = "/livros", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LivroController {

	@Autowired
	private LivroService livroService;

	@Operation(summary = "Lista livros de uma estante")
	@ApiResponse(responseCode = "200", description = "Lista de livros")
	@GetMapping
	public ResponseEntity<Page<LivrosDTO>> listar(
			@Parameter(description = "ID da estante", example = "1") @RequestParam Long estante,
			@ParameterObject @PageableDefault(page = 0, size = 24, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {

		return ResponseEntity.ok(this.livroService.listar(estante, paginacao));

	}

	@Operation(summary = "Pesquisa livros")
	@ApiResponse(responseCode = "200", description = "Lista de livros")
	@GetMapping("/pesquisar")
	public ResponseEntity<Page<LivrosDTO>> pesquisar(
			@Parameter(description = "ID da estante", example = "1") @RequestParam(required = false) Long estante,
			@Parameter(description = "Título do livro", example = "Sófocles") @RequestParam(required = false) String titulo, 
			@Parameter(description = "ISBN do livro", example = "9788575425770") @RequestParam(required = false) String isbn,
			@Parameter(description = "ID da editora", example = "1") @RequestParam(required = false) Long editora, 
			@Parameter(description = "ID do autor", example = "1") @RequestParam(required = false) Long autor,
			@Parameter(description = "ID do gênero", example = "1") @RequestParam(required = false) Long genero,
			@Parameter(description = "ID do idioma", example = "1") @RequestParam(required = false) Long idioma,
			@Parameter(description = "ID do tipo", example = "1") @RequestParam(required = false) Long tipo,
			@Parameter(description = "Ano do livro", example = "2010") @RequestParam(required = false) Integer ano,
			@Parameter(description = "Indica se está ativo ou não", example = "true") @RequestParam(required = false) Boolean ativo,
			@ParameterObject @PageableDefault(page = 0, size = 24, sort = "titulo", direction = Direction.ASC) Pageable paginacao) {

		return ResponseEntity.ok(this.livroService.pesquisar(estante, titulo, isbn, editora, autor, genero, idioma, tipo, ano, ativo, paginacao));

	}

	@Operation(summary = "Retorna um livro")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna um livro"),
		@ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@GetMapping("{id}")
	public ResponseEntity<LivroDTO> livro(@Parameter(description = "ID do livro", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(this.livroService.livro(id));
	}

	@Operation(summary = "Cadastra novo livro")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Livro cadastrado"),
		@ApiResponse(responseCode = "400", description = "Erro ao cadastrar livro", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDeFormularioDTO.class)))
	})
	@PostMapping()
	public ResponseEntity<LivroDTO> cadastrar(@RequestBody @Valid LivroForm livroForm, UriComponentsBuilder uriBuilder) {

		LivroDTO livroDTO = this.livroService.cadastrar(livroForm);
		
		log.info("Livro: "+ livroDTO.getTitulo() +" cadastrado com sucesso.");

		URI uri = uriBuilder.path("/livros/{id}").buildAndExpand(livroDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(livroDTO);
	}
	
	@Operation(summary = "Muda um livro de estante")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Livro mudado de estante"),
		@ApiResponse(responseCode = "404", description = "Livro ou Estante não encontrado(a)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@PutMapping("{idLivro}/estante/{idEstante}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void mudaEstante(@Parameter(description = "ID do livro", example = "1") @PathVariable Long idLivro, @Parameter(description = "ID da estante", example = "1")  @PathVariable Long idEstante) {
		this.livroService.mudaEstante(idLivro, idEstante);
		log.info("Livro "+ idLivro +" mudado para estante "+ idEstante);
	}
	
	@Operation(summary = "Ativar um livro")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Livro ativado"),
		@ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@PutMapping("{id}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@Parameter(description = "ID do livro", example = "1") @PathVariable Long id) {
		this.livroService.ativar(id);
		log.info("Livro "+ id +" ativado");
	}
	
	@Operation(summary = "Desativar um livro")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Livro deativado"),
		@ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroPadraoDTO.class)))
	})
	@DeleteMapping("{id}/desativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@Parameter(description = "ID do livro", example = "1") @PathVariable Long id) {
		this.livroService.desativar(id);

		log.info("Livro "+ id +" desativado");
	}

}
