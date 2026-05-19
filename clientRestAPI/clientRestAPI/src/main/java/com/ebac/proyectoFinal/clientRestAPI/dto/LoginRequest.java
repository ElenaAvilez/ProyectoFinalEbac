package com.ebac.proyectoFinal.clientRestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

    //nota
}