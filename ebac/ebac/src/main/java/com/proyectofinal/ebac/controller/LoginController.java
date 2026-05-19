package com.proyectofinal.ebac.controller;

import com.proyectofinal.ebac.dto.LoginRequest;
import com.proyectofinal.ebac.dto.Usuario;
import com.proyectofinal.ebac.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseWrapper<Usuario> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank()) {
            return new ResponseWrapper<>(
                    false,
                    "El usuario es requerido",
                    ResponseEntity.badRequest().build()
            );
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return new ResponseWrapper<>(
                    false,
                    "La contraseña es requerida",
                    ResponseEntity.badRequest().build()
            );
        }

        Optional<Usuario> usuarioOptional = usuarioService.validarLogin(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (usuarioOptional.isPresent()) {
            return new ResponseWrapper<>(
                    true,
                    "Login correcto",
                    ResponseEntity.ok(usuarioOptional.get())
            );
        }

        return new ResponseWrapper<>(
                false,
                "Usuario o contraseña incorrectos",
                ResponseEntity.status(401).build()
        );
    }
}
