package com.webapp.proyectofinal.feign;


import com.webapp.proyectofinal.dto.LoginRequest;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import com.webapp.proyectofinal.dto.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
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
