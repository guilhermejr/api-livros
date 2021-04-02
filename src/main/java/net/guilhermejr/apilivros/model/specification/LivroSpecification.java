package net.guilhermejr.apilivros.model.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.entity.Livro;

public class LivroSpecification {

	public static Specification<Livro> primeira() {
		return (root, query, builder) -> builder.isTrue(builder.literal(Boolean.TRUE));
	}

	public static Specification<Livro> titulo(String titulo) {

		if (StringUtils.hasText(titulo)) {
			return (root, query, builder) -> {
				Predicate p1 = builder.like(root.get("titulo"), "%" + titulo + "%");
				Predicate p2 = builder.like(root.get("subtitulo"), "%" + titulo + "%");
				return builder.or(p1, p2);
			};
		}

		return null;
	}

	public static Specification<Livro> estante(Long estante) {

		if (estante != null) {
			return (root, query, builder) -> builder.equal(root.get("estante").get("id"), estante);
		}

		return null;
	}

	public static Specification<Livro> isbn(String isbn) {

		if (StringUtils.hasText(isbn)) {
			return (root, query, builder) -> builder.equal(root.get("isbn"), isbn);
		}

		return null;
	}

	public static Specification<Livro> editora(Long editora) {

		if (editora != null) {
			return (root, query, builder) -> builder.equal(root.get("editora").get("id"), editora);
		}

		return null;
	}

	public static Specification<Livro> autor(Long autor) {

		if (autor != null) {

			return (root, query, builder) -> {
				Join<Autor, Livro> join = root.join("autores");
				return builder.equal(join.get("id"), autor);
			};
		}

		return null;
	}

	public static Specification<Livro> genero(Long genero) {

		if (genero != null) {

			return (root, query, builder) -> {
				Join<Genero, Livro> join = root.join("generos");
				return builder.equal(join.get("id"), genero);
			};
		}

		return null;
	}

	public static Specification<Livro> idioma(Long idioma) {

		if (idioma != null) {
			return (root, query, builder) -> builder.equal(root.get("idioma").get("id"), idioma);
		}

		return null;
	}
	
	public static Specification<Livro> tipo(Long tipo) {

		if (tipo != null) {
			return (root, query, builder) -> builder.equal(root.get("tipo").get("id"), tipo);
		}

		return null;
	}
	
	public static Specification<Livro> ano(Integer ano) {

		if (ano != null) {
			return (root, query, builder) -> builder.equal(root.get("anoPublicacao"), ano);
		}

		return null;
	}
	
	public static Specification<Livro> ativo(Boolean ativo) {

		if (ativo != null) {
			return (root, query, builder) -> builder.equal(root.get("ativo"), ativo);
		}

		return null;
	}

}
