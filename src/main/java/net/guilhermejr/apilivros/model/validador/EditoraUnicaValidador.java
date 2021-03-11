package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import net.guilhermejr.apilivros.model.annotation.EditoraUnica;
import net.guilhermejr.apilivros.model.repository.EditoraRepository;

public class EditoraUnicaValidador implements ConstraintValidator<EditoraUnica, String> {

	@Autowired
	private EditoraRepository editoraRepository;
	
	@Override
	public boolean isValid(String descricao, ConstraintValidatorContext context) {
		return !this.editoraRepository.existsByDescricao(descricao);
	}
	
}
