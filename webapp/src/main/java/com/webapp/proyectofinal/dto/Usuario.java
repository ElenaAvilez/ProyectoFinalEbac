package com.webapp.proyectofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Usuario {

    private Long idUsuario;
    private String username;
    private String password;
}
