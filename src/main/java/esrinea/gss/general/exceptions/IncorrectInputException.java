package esrinea.gss.general.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception that is thrown if the input from the user is not correct, for example, missing input, incorrect format or data type, etc.

 *
 */
public class IncorrectInputException extends RuntimeException {

	public IncorrectInputException(String message, Throwable err) {
		super(message, err);
	}
}
