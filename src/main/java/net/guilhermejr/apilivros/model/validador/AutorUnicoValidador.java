package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.annotation.AutorUnico;
import net.guilhermejr.apilivros.model.repository.AutorRepository;

@Component
public class AutorUnicoValidador implements ConstraintValidator<AutorUnico, String> {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Override
	public boolean isValid(String descricao, ConstraintValidatorContext context) {
		return !this.autorRepository.existsByDescricao(descricao);
	}

}
