package esrinea.gss.general.exceptions;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(String message, Throwable err) {
		super(message, err);
	}
}
