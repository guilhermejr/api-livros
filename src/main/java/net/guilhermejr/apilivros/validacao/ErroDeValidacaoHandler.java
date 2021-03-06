package net.guilhermejr.apilivros.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.exception.Exception401;
import net.guilhermejr.apilivros.exception.ExceptionNotFound;
import net.guilhermejr.apilivros.exception.ExceptionPadrao;

@Slf4j
@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Hidden
	@ExceptionHandler(Exception401.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErroPadraoDTO handleErroPadrao401(Exception401 ex, WebRequest request) {
		
		log.error(ex.getMessage(), ex);
		return new ErroPadraoDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());

	}
	
	@Hidden
	@ExceptionHandler(ExceptionPadrao.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErroPadraoDTO handleErrroPadrao(ExceptionPadrao ex, WebRequest request) {
		
		log.error(ex.getMessage(), ex);
		return new ErroPadraoDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

	}
	
	@Hidden
	@ExceptionHandler(ExceptionNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErroPadraoDTO handleErrroNotFound(ExceptionNotFound ex, WebRequest request) {
		
		log.error(ex.getMessage(), ex);
		return new ErroPadraoDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());

	}
	
	@Hidden
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ErroPadraoDTO handleErrroNotFound(HttpMediaTypeNotSupportedException ex, WebRequest request) {
		
		log.error(ex.getMessage(), ex);
		return new ErroPadraoDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Content-Type n??o suportado.");

	}

	@Hidden
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
