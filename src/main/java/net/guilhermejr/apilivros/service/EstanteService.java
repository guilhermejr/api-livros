package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.model.dto.EstanteDTO;
import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.mapper.EstanteMapper;
import net.guilhermejr.apilivros.model.repository.EstanteRepository;

@Service
public class EstanteService {

	@Autowired
	private EstanteRepository estanteRepository;
	
	@Autowired
	private EstanteMapper estanteMapper;
	
	@Cacheable(value = "listarEstantes")
	public List<EstanteDTO> listar() {
		return this.estanteMapper.mapList(this.estanteRepository.findByOrderByDescricao());
	}
	
	@Cacheable(value = "estante")
	public EstanteDTO estante(Long id) {
		Optional<Estante> estante = this.estanteRepository.findById(id);
		return this.estanteMapper.mapObject(estante.orElseThrow(() -> new ExceptionNotFound("Estante "+ id +" não encontrada.")));
	}
	
}
