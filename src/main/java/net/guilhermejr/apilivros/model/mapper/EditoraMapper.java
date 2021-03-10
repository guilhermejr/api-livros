package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.EditoraDTO;
import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.form.EditoraForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class EditoraMapper extends MapperUtil {
	
	public List<EditoraDTO> mapList(List<Editora> editora) {
		return this.mapList(editora, EditoraDTO.class);
	}
	
	public EditoraDTO mapObject(Editora editora) {
		return super.mapObject(editora, EditoraDTO.class);
	}
	
	public Editora mapObject(EditoraForm editoraForm) {
		return super.mapObject(editoraForm, Editora.class);
	}

}
