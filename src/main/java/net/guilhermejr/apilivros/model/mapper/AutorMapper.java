package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.AutorDTO;
import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class AutorMapper extends MapperUtil {
	
	public List<AutorDTO> mapList(List<Autor> autor) {
		return this.mapList(autor, AutorDTO.class);
	}

}
