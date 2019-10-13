package esrinea.gss.shopping.operation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class representing the output of the operation with the Data being the data sent, and message and code are the succes message and codes
 */
@JsonInclude(value = Include.NON_NULL)
public class OperationDTO<T> {
	
	private T data;
	private int code;
	private String message;
	public OperationDTO(T object, String string, int i) {
		data = object;
		message = string;
		code = i;
	}
	public OperationDTO() {
		// TODO Auto-generated constructor stub
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
