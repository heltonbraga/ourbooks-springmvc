package br.com.braga.ourbooks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SimpleValidationHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<SimpleParameterError> handle(MethodArgumentNotValidException exception) {
		List<SimpleParameterError> dto = new ArrayList<>();

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			SimpleParameterError erro = new SimpleParameterError(e.getField(), mensagem);
			dto.add(erro);
		});

		return dto;
	}

	static class SimpleParameterError {

		private String campo;
		private String erro;

		public SimpleParameterError(String campo, String erro) {
			this.campo = campo;
			this.erro = erro;
		}

		public String getCampo() {
			return campo;
		}

		public String getErro() {
			return erro;
		}

	}
}
