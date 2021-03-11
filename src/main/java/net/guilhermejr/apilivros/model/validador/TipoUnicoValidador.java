package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.annotation.TipoUnico;
import net.guilhermejr.apilivros.model.repository.TipoRepository;

@Component
public class TipoUnicoValidador implements ConstraintValidator<TipoUnico, String> {
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Override
	public boolean isValid(String descricao, ConstraintValidatorContext context) {
		return !this.tipoRepository.existsByDescricao(descricao);
	}

}
