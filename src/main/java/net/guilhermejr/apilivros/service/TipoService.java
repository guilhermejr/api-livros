package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.TipoDTO;
import net.guilhermejr.apilivros.model.entity.Tipo;
import net.guilhermejr.apilivros.model.form.TipoForm;
import net.guilhermejr.apilivros.model.mapper.TipoMapper;
import net.guilhermejr.apilivros.model.repository.TipoRepository;

@Service
public class TipoService {
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Autowired
	private TipoMapper tipoMapper;
	
	public List<TipoDTO> listar() {
		return this.tipoMapper.mapList(this.tipoRepository.findByOrderByDescricao());
	}
	
	public TipoDTO tipo(Long id) {
		Optional<Tipo> tipo = this.tipoRepository.findById(id);
		return this.tipoMapper.mapObject(tipo.orElseThrow(() -> new ExceptionNotFound("Tipo "+ id +" não encontrado.")));
	}
	
	public TipoDTO cadastrar(TipoForm tipoForm) {
		return this.tipoMapper.mapObject(this.tipoRepository.save(this.tipoMapper.mapObject(tipoForm)));
	}

}
