package net.guilhermejr.apilivros.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.exception.ExceptionPadrao;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ExceptionPadrao.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErroPadraoDTO handleErrroPadrao(ExceptionPadrao ex, WebRequest request) {
		
		return new ErroPadraoDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

	}
	
	@ExceptionHandler(ExceptionNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErroPadraoDTO handleErrroNotFound(ExceptionNotFound ex, WebRequest request) {
		
		return new ErroPadraoDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		
		List<ErroDeFormularioDTO> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}

}
