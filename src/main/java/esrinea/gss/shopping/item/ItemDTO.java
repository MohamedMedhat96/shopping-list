package esrinea.gss.shopping.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class representing the generic JSON sent to the user in order to display
 * the output data related to his items request
 */
@JsonInclude(Include.NON_NULL)
public class ItemDTO<T> {

	private T data;
	private String message;
	private int statusCode;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return statusCode;
	}

	public void setCode(int code) {
		this.statusCode = code;
	}

}
