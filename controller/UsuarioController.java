package com.homework.security.controller;

import com.homework.security.dto.UsuarioRequestDTO;
import com.homework.security.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService service;
    private AuthenticationManager manager;

    public UsuarioController(final UsuarioService service, AuthenticationManager manager) {
        this.service = service;
        this.manager = manager;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UsuarioRequestDTO dto){
        service.logincheck(dto);
        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.nome(),
                            dto.senha()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Senha incorreta.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Bem vindo(a) "+ dto.nome());
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody UsuarioRequestDTO dto){
        service.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso!");
    }

    @GetMapping("/filmes")
    public ResponseEntity<List<String>> listaDeContratos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarFilmes());
    }
}
