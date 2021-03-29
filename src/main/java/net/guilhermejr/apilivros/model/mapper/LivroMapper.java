package net.guilhermejr.apilivros.model.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.LivroDTO;
import net.guilhermejr.apilivros.model.dto.LivrosDTO;
import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.entity.Livro;
import net.guilhermejr.apilivros.model.form.LivroForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class LivroMapper extends MapperUtil {
	
	public LivroMapper() {
		
		Converter<List<String>, List<Autor>> autoresConverter = ctx -> { 
			
			List<Autor> autores = new ArrayList<>();
			ctx.getSource().forEach(a -> {
				autores.add(Autor.builder().descricao(a).build());
			});
			return autores;
		};		
		
		Converter<List<String>, List<Genero>> generosConverter = ctx -> { 
			
			List<Genero> generos = new ArrayList<>();
			ctx.getSource().forEach(g -> {
				generos.add(Genero.builder().descricao(g).build());
			});
			return generos;
		};		
		
		this.modelMapper.createTypeMap(LivroForm.class, Livro.class)
			.addMappings(mapper -> mapper.using(autoresConverter).map(LivroForm::getAutores, Livro::setAutores))
			.addMappings(mapper -> mapper.using(generosConverter).map(LivroForm::getGeneros, Livro::setGeneros))
			.<String>addMapping(src -> src.getEditora(), (dest, v) -> dest.getEditora().setDescricao(v))
			.<String>addMapping(src -> src.getIdioma(), (dest, v) -> dest.getIdioma().setDescricao(v))
			.<String>addMapping(src -> src.getTipo(), (dest, v) -> { if (v == null || v.isEmpty()) { v = "1"; } dest.getTipo().setId(Long.parseLong(v)); })
			.<String>addMapping(src -> src.getEstante() , (dest, v) -> { if (v == null || v.isEmpty()) { v = "1"; } dest.getEstante().setId(Long.parseLong(v)); });
	}
	
	public LivroDTO mapObject(Livro livro) {
		return this.mapObject(livro, LivroDTO.class);
	}
	
	public Livro mapObject(LivroForm livroForm) {
		return this.mapObject(livroForm, Livro.class);
	}
	
	public Page<LivrosDTO> mapPage(Page<Livro> livro) {
		return this.mapPage(livro, LivrosDTO.class);
	}	
	
}
