package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.form.AutorForm;
import net.guilhermejr.apilivros.model.mapper.AutorMapper;
import net.guilhermejr.apilivros.model.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private AutorMapper autorMapper;
	
	public List<AutorDTO> listar() {
		return this.autorMapper.mapList(this.autorRepository.findByOrderByDescricao());
	}
	
	public AutorDTO autor(Long id) {
		Optional<Autor> autor = this.autorRepository.findById(id);
		return this.autorMapper.mapObject(autor.orElseThrow(() -> new ExceptionNotFound("Autor não encontrado.")));
	}
	
	public AutorDTO cadastrar(AutorForm autorForm) {
		return this.autorMapper.mapObject(this.autorRepository.save(this.autorMapper.mapObject(autorForm)));
	}

}
