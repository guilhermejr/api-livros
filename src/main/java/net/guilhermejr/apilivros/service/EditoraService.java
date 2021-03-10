package net.guilhermejr.apilivros.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.dto.EditoraDTO;
import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.form.EditoraForm;
import net.guilhermejr.apilivros.model.mapper.EditoraMapper;
import net.guilhermejr.apilivros.model.repository.EditoraRepository;

@Service
public class EditoraService {

	@Autowired
	private EditoraRepository editoraRepository;
	
	@Autowired
	private EditoraMapper editoraMapper;
	
	public List<EditoraDTO> listar() {
		return this.editoraMapper.mapList(this.editoraRepository.findByOrderByDescricao());
	}
	
	public EditoraDTO editora(Long id) {
		Optional<Editora> editora = this.editoraRepository.findById(id);
		return this.editoraMapper.mapObject(editora.orElseThrow(() -> new ExceptionPadrao("Editora não encontrada.")));
	}
	
	public EditoraDTO cadastrar(EditoraForm editoraForm) {
		return this.editoraMapper.mapObject(this.editoraRepository.save(this.editoraMapper.mapObject(editoraForm)));
	}
	
}
