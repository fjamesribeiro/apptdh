package br.com.zoi.apptdah.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestErrorMessage {

	private HttpStatus status;

	private String message;

	public RestErrorMessage(HttpStatus status, String message) {
		this.status = status;
	}
	
	

}
