package application.controller;

import application.dto.ResponseDto;
import org.springframework.http.HttpStatus;

public abstract class Controller {

	// URL: http://localhost:8080/swagger-ui/

	public static HttpStatus getHttpStatus(ResponseDto serviceResult) {
		switch (serviceResult.getStatusKode()) {
			case OK:
				return HttpStatus.OK;
			case INPUT_FEJL:
				return HttpStatus.BAD_REQUEST;
			case TEKNISK_FEJL:
				return HttpStatus.INTERNAL_SERVER_ERROR;
			default:
				return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
