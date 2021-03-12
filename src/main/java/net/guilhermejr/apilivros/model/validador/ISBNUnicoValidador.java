package net.guilhermejr.apilivros.model.validador;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.annotation.ISBNUnico;
import net.guilhermejr.apilivros.model.repository.LivroRepository;

@Component
public class ISBNUnicoValidador implements ConstraintValidator<ISBNUnico, String> {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Override
	public boolean isValid(String isbn, ConstraintValidatorContext context) {
		return !this.livroRepository.existsByIsbn(isbn);
	}

}
