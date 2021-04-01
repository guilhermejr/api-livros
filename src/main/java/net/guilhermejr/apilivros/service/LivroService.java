package net.guilhermejr.apilivros.service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.LivroDTO;
import net.guilhermejr.apilivros.model.dto.LivrosDTO;
import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.entity.Livro;
import net.guilhermejr.apilivros.model.entity.Tipo;
import net.guilhermejr.apilivros.model.form.LivroForm;
import net.guilhermejr.apilivros.model.mapper.LivroMapper;
import net.guilhermejr.apilivros.model.repository.AutorRepository;
import net.guilhermejr.apilivros.model.repository.EditoraRepository;
import net.guilhermejr.apilivros.model.repository.EstanteRepository;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;
import net.guilhermejr.apilivros.model.repository.IdiomaRepository;
import net.guilhermejr.apilivros.model.repository.LivroRepository;
import net.guilhermejr.apilivros.model.repository.TipoRepository;
import net.guilhermejr.apilivros.model.specification.LivroSpecification;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private EditoraRepository editoraRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private EstanteRepository estanteRepository;

	@Autowired
	private IdiomaRepository idiomaRepository;

	@Autowired
	private GeneroRepository generoRepository;

	@Autowired
	private LivroMapper livroMapper;

	@Value("${livro.localCapa}")
	private String localCapa;

	public Page<LivrosDTO> listar(Long estante, Pageable paginacao) {

		Page<Livro> livros = this.livroRepository.findByEstanteId(estante, paginacao);

		return this.livroMapper.mapPage(livros);

	}

	public Page<LivrosDTO> pesquisar(Long estante, String titulo, String isbn, Long editora, Long autor, Long genero, Long idioma, Long tipo, Integer ano,
			Pageable paginacao) {

		Page<Livro> livros = this.livroRepository
				.findAll(
						LivroSpecification.primeira().
							and(LivroSpecification.titulo(titulo))
							.and(LivroSpecification.isbn(isbn))
							.and(LivroSpecification.estante(estante))
							.and(LivroSpecification.editora(editora))
							.and(LivroSpecification.autor(autor))
							.and(LivroSpecification.genero(genero))
							.and(LivroSpecification.idioma(idioma))
							.and(LivroSpecification.tipo(tipo))
							.and(LivroSpecification.ano(ano)),
						paginacao);

		return this.livroMapper.mapPage(livros);
	}

	public LivroDTO livro(Long id) {
		Optional<Livro> livro = this.livroRepository.findById(id);
		return this.livroMapper.mapObject(livro.orElseThrow(() -> new ExceptionNotFound("Livro "+ id +" não encontrado.")));
	}

	@Transactional
	public LivroDTO cadastrar(LivroForm livroForm) {

		Livro livro = this.livroMapper.mapObject(livroForm);

		// --- Editora ---
		Editora editora = editoraRepository.findByDescricao(livro.getEditora().getDescricao());
		if (editora == null) {
			editora = Editora.builder().descricao(livro.getEditora().getDescricao()).build();
			editoraRepository.save(editora);
		}
		livro.setEditora(editora);

		// --- Idioma ---
		Idioma idioma = idiomaRepository.findByDescricao(livro.getIdioma().getDescricao());
		if (idioma == null) {
			idioma = Idioma.builder().descricao(livro.getIdioma().getDescricao()).build();
			idiomaRepository.save(idioma);
		}
		livro.setIdioma(idioma);

		// --- Tipo ---
		Tipo tipo = tipoRepository.findById(livro.getTipo().getId()).get();
		livro.setTipo(tipo);

		// --- Estante ---
		Estante estante = estanteRepository.findById(livro.getEstante().getId()).get();
		livro.setEstante(estante);

		// --- Autor ---
		List<Autor> autores = new ArrayList<>();
		livro.getAutores().forEach(a -> {
			Autor autor = autorRepository.findByDescricao(a.getDescricao());
			if (autor == null) {
				autor = Autor.builder().descricao(a.getDescricao()).build();
				autorRepository.save(autor);
			}
			autores.add(autor);
		});
		livro.setAutores(autores);

		// --- Genero ---
		List<Genero> generos = new ArrayList<>();
		livro.getGeneros().forEach(g -> {
			Genero genero = generoRepository.findByDescricao(g.getDescricao());
			if (genero == null) {
				genero = Genero.builder().descricao(g.getDescricao()).build();
				generoRepository.save(genero);
			}
			generos.add(genero);
		});
		livro.setGeneros(generos);

//		ObjectMapper map = new ObjectMapper();
//		try {
//			System.out.println(map.writeValueAsString(livroForm));
//			System.out.println(map.writeValueAsString(livro));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

		// --- Salva livro ---
		livroRepository.save(livro);

		// --- Download do arquivo da capa ---
		try {
			URL url = new URL(livro.getCapa());
			String caminho = localCapa + livro.getId() + "." + livroForm.getExtensao();
			System.out.println(caminho);
			File file = new File(caminho);
			FileUtils.copyURLToFile(url, file);
		} catch (Exception e) {
			System.out.println("MENSAGEM DE ERRO: " + e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return this.livroMapper.mapObject(livro);

	}

}
