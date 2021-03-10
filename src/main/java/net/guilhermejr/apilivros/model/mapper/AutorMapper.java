package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.form.AutorForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class AutorMapper extends MapperUtil {
	
	public List<AutorDTO> mapList(List<Autor> autorList) {
		return super.mapList(autorList, AutorDTO.class);
	}
	
	public AutorDTO mapObject(Autor autor) {
		return super.mapObject(autor, AutorDTO.class);
	}
	
	public Autor mapObject(AutorForm autorForm) {
		return super.mapObject(autorForm, Autor.class);
	}

}
