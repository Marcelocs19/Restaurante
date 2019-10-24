package br.com.restaurante.erro;

import java.sql.SQLIntegrityConstraintViolationException;
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

@RestControllerAdvice
public class ErroValidacao {

	@Autowired
	private MessageSource messageSource;
	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value =  {MethodArgumentNotValidException.class,IllegalArgumentException.class,NullPointerException.class})
	public List<ErroValidacaoDto> badRequest(MethodArgumentNotValidException exception) {
		List<ErroValidacaoDto> listErrorValidationDto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String messageError = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroValidacaoDto error = new ErroValidacaoDto(e.getField(), messageError);
			listErrorValidationDto.add(error);
		});
		return listErrorValidationDto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value =  {SQLIntegrityConstraintViolationException.class})
	public List<ErroValidacaoDto> internalServer(MethodArgumentNotValidException exception) {
		List<ErroValidacaoDto> listErrorValidationDto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String messageError = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroValidacaoDto error = new ErroValidacaoDto(e.getField(), messageError);
			listErrorValidationDto.add(error);
		});
		return listErrorValidationDto;
	}
	
}
