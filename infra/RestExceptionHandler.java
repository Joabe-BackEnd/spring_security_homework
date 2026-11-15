package com.homework.security.infra;

import com.homework.security.exceptions.UsuarioJaCadastradoException;
import com.homework.security.exceptions.UsuarioNaoCadastradoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UsuarioJaCadastradoException.class)
    private ResponseEntity<String> usuarioJaCadastrado(UsuarioJaCadastradoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já cadastrado.");
    }

    @ExceptionHandler(UsuarioNaoCadastradoException.class)
    private ResponseEntity<String> usuarioNaoCadastrado(UsuarioNaoCadastradoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não registrado.");
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        erros.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }
}
