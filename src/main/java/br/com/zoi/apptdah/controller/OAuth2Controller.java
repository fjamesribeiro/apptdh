package br.com.zoi.apptdah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zoi.apptdah.config.security.JwtService;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {
	
    @Autowired
    private JwtService jwtService;
    
    @GetMapping("/success")
    public ResponseEntity<String> oauth2Success(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return ResponseEntity.ok("Login com Google bem-sucedido! Email: " + email);
    }
    
    @GetMapping("/google/token")
    public String getJwtForGoogle(@AuthenticationPrincipal OAuth2User principal) {
        return jwtService.generateTokenForGoogle(principal);
    }
    
}
