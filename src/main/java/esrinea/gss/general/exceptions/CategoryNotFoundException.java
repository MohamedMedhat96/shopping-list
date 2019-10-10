package esrinea.gss.general.exceptions;

public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException(String message, Exception e)
	{
		super(message,e);
	}
}
