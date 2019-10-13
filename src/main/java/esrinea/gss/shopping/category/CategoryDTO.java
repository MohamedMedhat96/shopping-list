package esrinea.gss.shopping.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
A generic Category Response, with the Data being sent, a Success message and a Success code.
*/
@JsonInclude(Include.NON_NULL)
public class CategoryDTO<T> {
	
	private T data;
	private String message;
	private int statusCode;

	
	public int getStatusCode() {
		return statusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		statusCode = httpStatusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setHttpMessage(String httpMessage) {
		message = httpMessage;
	}
	public T getData() {
		
			return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
