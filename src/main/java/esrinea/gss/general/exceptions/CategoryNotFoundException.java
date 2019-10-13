package esrinea.gss.general.exceptions;


//An exception for when a category that was searched for was not found.
public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException(String message, Exception e)
	{
		super(message,e);
	}
}
