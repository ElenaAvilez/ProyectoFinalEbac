package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.LoginRequest;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import com.ebac.proyectoFinal.clientRestAPI.dto.Usuario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignServiceLogin {

    public final FeignApiClientLogin feignApiClientLogin;
    public final String apiUrl;

    public FeignServiceLogin(FeignApiClientLogin feignApiClientLogin, String apiUrl) {
        this.feignApiClientLogin = feignApiClientLogin;
        this.apiUrl = apiUrl;
    }

    public ResponseWrapper<Usuario> login(LoginRequest loginRequest) {
        log.info("Validando login contra api {}", apiUrl);
        return feignApiClientLogin.login(loginRequest);
    }
}
