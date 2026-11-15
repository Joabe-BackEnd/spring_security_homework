package com.homework.security.service;

import com.homework.security.dto.UsuarioRequestDTO;
import com.homework.security.exceptions.UsuarioJaCadastradoException;
import com.homework.security.exceptions.UsuarioNaoCadastradoException;
import com.homework.security.model.Usuario;
import com.homework.security.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;


    public UsuarioService(final UsuarioRepository repository,
                          final PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public void logincheck(UsuarioRequestDTO dto){
        if (repository.findByNome(dto.nome()).isEmpty()){
            throw new UsuarioNaoCadastradoException("Usuário não registrado.");
        }
    }

    public void criarUsuario(UsuarioRequestDTO dto){
        if (repository.findByNome(dto.nome()).isPresent()){
            throw new UsuarioJaCadastradoException("Este nome de usuário já está em uso, tente outro nome para prosseguir.");
        }
        Usuario u = new Usuario();
        u.setNome(dto.nome());
        u.setSenha(encoder.encode(dto.senha()));
        repository.save(u);
    }

    public List<String> listarFilmes(){
        return List.of("Interestelar",
                "O Poderoso Chefão",
                "Clube da Luta",
                "Matrix",
                "A Origem",
                "Pulp Fiction",
                "Forrest Gump",
                "Gladiador",
                "O Senhor dos Anéis",
                "Batman: O Cavaleiro das Trevas");
    }
}
