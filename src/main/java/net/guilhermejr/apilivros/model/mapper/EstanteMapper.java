package net.guilhermejr.apilivros.model.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.dto.EstanteDTO;
import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.form.EstanteForm;
import net.guilhermejr.apilivros.utils.MapperUtil;

@Component
public class EstanteMapper extends MapperUtil {
	
	public List<EstanteDTO> mapList(List<Estante> estante) {
		return this.mapList(estante, EstanteDTO.class);
	}
	
	public EstanteDTO mapObject(Estante estante) {
		return super.mapObject(estante, EstanteDTO.class);
	}
	
	public Estante mapObject(EstanteForm estanteForm) {
		return super.mapObject(estanteForm, Estante.class);
	}

}
