package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.LoginRequest;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import com.ebac.proyectoFinal.clientRestAPI.dto.Usuario;
import feign.Headers;
import feign.RequestLine;

public interface FeignApiClientLogin {

    @RequestLine("POST /login")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Usuario> login(LoginRequest loginRequest);
}