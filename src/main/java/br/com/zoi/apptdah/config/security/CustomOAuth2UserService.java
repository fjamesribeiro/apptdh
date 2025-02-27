package br.com.zoi.apptdah.config.security;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.zoi.apptdah.model.Role;
import br.com.zoi.apptdah.model.RoleName;
import br.com.zoi.apptdah.model.Usuario;
import br.com.zoi.apptdah.repository.RoleRepository;
import br.com.zoi.apptdah.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        // Obtém os atributos do usuário
        String email = oAuth2User.getAttribute("email");
        String nome = oAuth2User.getAttribute("name");
        String sobrenome = oAuth2User.getAttribute("family_name");
        if (sobrenome == null) {
            sobrenome = "Não informado"; // Define um valor padrão
        }        

        // Verifica se o usuário já existe no banco
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        
        if (usuarioExistente.isEmpty()) {
            // Busca a role padrão ROLE_USER
            Role roleUser = roleRepository.findByNome(RoleName.ROLE_USER.toString())
                    .orElseThrow(() -> new RuntimeException("Role ROLE_USER não encontrada!"));

            // Cria um novo usuário
            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email);
            novoUsuario.setNome(nome);
            novoUsuario.setSobrenome(sobrenome); // Agora nunca será nulo            
            novoUsuario.setSenha(""); // O usuário do Google não precisa de senha
            novoUsuario.setRoles(Set.of(roleUser));

            usuarioRepository.save(novoUsuario);
        }

        return oAuth2User;
    }
}