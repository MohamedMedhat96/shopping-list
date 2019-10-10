package esrinea.gss.general.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;


public class IncorrectInput extends RuntimeException {

	public IncorrectInput(String message, Throwable err)
	{
		super(message,err);
	}
}
