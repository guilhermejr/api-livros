package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.annotation.IdiomaUnico;
import net.guilhermejr.apilivros.model.repository.IdiomaRepository;

@Component
public class IdiomaUnicoValidador implements ConstraintValidator<IdiomaUnico, String> {
	
	@Autowired
	private IdiomaRepository idiomaRepository;
	
	@Override
	public boolean isValid(String idiomaNome, ConstraintValidatorContext context) {
		return !this.idiomaRepository.existsByDescricao(idiomaNome);
	}

}
