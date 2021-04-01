package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.TipoDTO;
import net.guilhermejr.apilivros.model.entity.Tipo;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class TipoMapper extends MapperUtil {
	
	public List<TipoDTO> mapList(List<Tipo> tipo) {
		return this.mapList(tipo, TipoDTO.class);
	}
	
	public TipoDTO mapObject(Tipo tipo) {
		return super.mapObject(tipo, TipoDTO.class);
	}

}