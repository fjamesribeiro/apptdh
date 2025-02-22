package br.com.zoi.apptdah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zoi.apptdah.config.security.JwtService;
import br.com.zoi.apptdah.dto.LoginDto;

@Service
public class AuthenticationService {

	@Autowired
	private JwtService jwtService;

	public String authenticate(LoginDto authentication) {
		return jwtService.generateToken(authentication);
	}
}
