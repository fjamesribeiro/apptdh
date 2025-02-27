package br.com.zoi.apptdah.config.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.com.zoi.apptdah.dto.LoginDto;

@Service
public class JwtService {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String generateToken(LoginDto dto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

		Instant now = Instant.now();
		long expiry = 8640L; //1 dia

		String scopes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		var claims = JwtClaimsSet.builder().issuer("app-tdah").issuedAt(now).expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName()).claim("scope", scopes).build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	public String generateTokenForGoogle(OAuth2User principal) {
	    Instant now = Instant.now();
	    long expiry = 86400L; // 1 dia

	    String email = principal.getAttribute("email");
	    String name = principal.getAttribute("name");

	    var claims = JwtClaimsSet.builder()
	            .issuer("app-tdah")
	            .issuedAt(now)
	            .expiresAt(now.plusSeconds(expiry))
	            .subject(email) // O email do usuário será o subject do token
	            .claim("name", name)
	            .claim("scope", "USER") // Definindo uma role padrão para usuários do Google
	            .build();

	    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	

}
