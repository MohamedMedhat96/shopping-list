package esrinea.gss.general;

//Generic Error Response Message
public class Response<T> {

	private String message; // Error Message loaded from the htmlmessages.properties which is in the
							// resources
	private int code;      //code for the error such as 404,401,etc.

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
