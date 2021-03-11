package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.annotation.GeneroUnico;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;

@Component
public class GeneroUnicoValidador implements ConstraintValidator<GeneroUnico, String> {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Override
	public boolean isValid(String descricao, ConstraintValidatorContext context) {
		return !this.generoRepository.existsByDescricao(descricao);
	}

}
