package esrinea.gss.general.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import esrinea.gss.general.Response;

/**
 * An exception handler for the whole application, the handler extends Spring's
 * ResponseEntityExceptionHandler and it is treated as a spring controller advice
 */

@PropertySource("classpath:httpmessages.properties")
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private Environment env;

	// Handler for Incorrect Input Exceptions, the details of the exact exception is
	// explained inside the exception file
	@ExceptionHandler(value = { IncorrectInputException.class })
	protected ResponseEntity<Object> incorrectInput(RuntimeException ex, WebRequest request) {
		Response response = new Response();
		String code = env.getProperty("http.codes.userinput");
		String message = env.getProperty("http.error.userinput");
		response.setCode(HttpStatus.valueOf(code).value());
		response.setMessage(message + ": " + ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.valueOf(code));
	}

	// Handler for Items and Categories Not Found Exceptions, the details of the
	// exact exception is explained inside the exception file
	@ExceptionHandler(value = { ItemNotFoundException.class, CategoryNotFoundException.class })
	protected ResponseEntity<Object> itemNotFound(RuntimeException ex, WebRequest request) {
		Response response = new Response();
		String code = env.getProperty("http.codes.itemnotfound");
		String message = env.getProperty("http.error.itemnotfound");

		if (ex instanceof CategoryNotFoundException)
			message = "The category " + message;
		if (ex instanceof ItemNotFoundException)
			message = "The item " + message;

		response.setCode(HttpStatus.valueOf(code).value());
		response.setMessage(message);
		return new ResponseEntity<>(response, HttpStatus.valueOf(code));
	}
}
