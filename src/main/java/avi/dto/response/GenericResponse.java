package avi.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenericResponse<T> {
	int statusCode;
	String message;
	T result;

	public GenericResponse(int statusCode, T result) {
		super();
		this.statusCode = statusCode;
		this.result = result;
	}
}

