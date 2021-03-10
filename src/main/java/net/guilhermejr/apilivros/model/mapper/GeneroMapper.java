package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.GeneroDTO;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.form.GeneroForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class GeneroMapper  extends MapperUtil {
	
	public List<GeneroDTO> mapList(List<Genero> genero) {
		return this.mapList(genero, GeneroDTO.class);
	}
	
	public GeneroDTO mapObject(Genero genero) {
		return super.mapObject(genero, GeneroDTO.class);
	}
	
	public Genero mapObject(GeneroForm generoForm) {
		return super.mapObject(generoForm, Genero.class);
	}

}
