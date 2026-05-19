package com.proyectofinal.ebac.service;

import com.proyectofinal.ebac.dto.Usuario;
import com.proyectofinal.ebac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> validarLogin(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }
}
