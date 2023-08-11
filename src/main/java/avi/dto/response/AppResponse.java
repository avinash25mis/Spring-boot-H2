package avi.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class AppResponse<T> {
	int statusCode;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String process;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	T result;

	public AppResponse(int statusCode, T result) {
		super();
		this.statusCode = statusCode;
		this.result = result;
	}
	public AppResponse(int statusCode,String message, T result) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.result = result;
	}

	public AppResponse(int statusCode,String process,String message, T result) {
		super();
		this.statusCode = statusCode;
		this.process = process;
		this.message = message;
		this.result = result;
	}
}

