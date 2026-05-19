package com.webapp.proyectofinal.feign;


import com.webapp.proyectofinal.dto.LoginRequest;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import com.webapp.proyectofinal.dto.Usuario;
import feign.Headers;
import feign.RequestLine;

public interface FeignApiClientLogin {

    @RequestLine("POST /login")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Usuario> login(LoginRequest loginRequest);
}