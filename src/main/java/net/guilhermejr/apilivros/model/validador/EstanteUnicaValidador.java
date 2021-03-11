package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import net.guilhermejr.apilivros.model.annotation.EstanteUnica;
import net.guilhermejr.apilivros.model.repository.EstanteRepository;

public class EstanteUnicaValidador implements ConstraintValidator<EstanteUnica, String> {

	@Autowired
	private EstanteRepository estanteRepository;
	
	@Override
	public boolean isValid(String descricao, ConstraintValidatorContext context) {
		return !this.estanteRepository.existsByDescricao(descricao);
	}
	
}
