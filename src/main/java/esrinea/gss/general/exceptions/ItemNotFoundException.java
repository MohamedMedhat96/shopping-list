package esrinea.gss.general.exceptions;

/**
 * An exception that is thrown if the item the user searched for was not found.

 */
public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String message, Throwable err) {
		super(message, err);
	}
}
