package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.IdiomaDTO;
import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.form.IdiomaForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class IdiomaMapper  extends MapperUtil {
	
	public List<IdiomaDTO> mapList(List<Idioma> idioma) {
		return this.mapList(idioma, IdiomaDTO.class);
	}
	
	public IdiomaDTO mapObject(Idioma editora) {
		return super.mapObject(editora, IdiomaDTO.class);
	}
	
	public Idioma mapObject(IdiomaForm idiomaForm) {
		return super.mapObject(idiomaForm, Idioma.class);
	}

}