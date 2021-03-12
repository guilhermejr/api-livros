package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.GeneroDTO;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.form.GeneroForm;
import net.guilhermejr.apilivros.model.mapper.GeneroMapper;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;

@Service
public class GeneroService {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private GeneroMapper generoMapper;
	
	public List<GeneroDTO> listar() {
		return this.generoMapper.mapList(this.generoRepository.findByOrderByDescricao());
	}
	
	public GeneroDTO genero(Long id) {
		Optional<Genero> genero = this.generoRepository.findById(id);
		return this.generoMapper.mapObject(genero.orElseThrow(() -> new ExceptionNotFound("Gênero não encontrado.")));
	}
	
	public GeneroDTO cadastrar(GeneroForm generoForm) {
		return this.generoMapper.mapObject(this.generoRepository.save(this.generoMapper.mapObject(generoForm)));
	}

}
