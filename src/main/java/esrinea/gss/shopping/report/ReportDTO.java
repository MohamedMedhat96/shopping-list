package esrinea.gss.shopping.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)

/** 
 A JSON format for all reports given the data, the message and the code
 */
public class ReportDTO<T>{
	
	private T data;
	private String message;
	private int code;

	
	public ReportDTO(T object, String string, int i) {
		setData(object);
		setMessage(string);
		setCode(i);
	}

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
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
