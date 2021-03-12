package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.IdiomaDTO;
import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.form.IdiomaForm;
import net.guilhermejr.apilivros.model.mapper.IdiomaMapper;
import net.guilhermejr.apilivros.model.repository.IdiomaRepository;

@Service
public class IdiomaService {
	
	@Autowired
	private IdiomaRepository idiomaRepository;
	
	@Autowired
	private IdiomaMapper idiomaMapper;
	
	public List<IdiomaDTO> listar() {
		return this.idiomaMapper.mapList(this.idiomaRepository.findByOrderByDescricao());
	}
	
	public IdiomaDTO idioma(Long id) {
		Optional<Idioma> idioma = this.idiomaRepository.findById(id);
		return this.idiomaMapper.mapObject(idioma.orElseThrow(() -> new ExceptionNotFound("Idioma não encontrado.")));
	}
	
	public IdiomaDTO cadastrar(IdiomaForm idiomaForm) {
		return this.idiomaMapper.mapObject(this.idiomaRepository.save(this.idiomaMapper.mapObject(idiomaForm)));
	}

}
