package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.GeneroDTO;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class GeneroMapper  extends MapperUtil {
	
	public List<GeneroDTO> mapList(List<Genero> genero) {
		return this.mapList(genero, GeneroDTO.class);
	}

}
