package br.com.alura.forum.config;

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
import br.com.alura.forum.dto.ErroFormDto;

@RestControllerAdvice
public class ErrorHandler {

	@Autowired
	private MessageSource ms;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormDto> handle(MethodArgumentNotValidException ex) {
		List<ErroFormDto> ef = new ArrayList<>();
		List<FieldError> erros = ex.getBindingResult().getFieldErrors();
		
		erros.forEach(e -> {
			String mensagem = ms.getMessage(e, LocaleContextHolder.getLocale());
			ErroFormDto efd = new ErroFormDto(e.getField(), mensagem);
			ef.add(efd);
		});
		
		return ef;
	}
}
