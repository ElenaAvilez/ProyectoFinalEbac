package com.webapp.proyectofinal.controller;

import com.webapp.proyectofinal.dto.LoginRequest;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import com.webapp.proyectofinal.dto.Usuario;
import com.webapp.proyectofinal.feign.FeignServiceLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    FeignServiceLogin feignServiceLogin;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pagina-login");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            LoginRequest loginRequest = LoginRequest.builder()
                    .username(username)
                    .password(password)
                    .build();

            ResponseWrapper<Usuario> loginResponse = feignServiceLogin.login(loginRequest);

            if (loginResponse != null && loginResponse.isSuccess()) {
                request.getSession().setAttribute("username", username);
                response.sendRedirect("/");
                return null;
            }

            model.addAttribute("errorLogin", "Usuario o contraseña incorrectos");

        } catch (Exception e) {
            log.error("Error al iniciar sesión", e);
            model.addAttribute("errorLogin", "No fue posible validar el usuario");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pagina-login");

        request.getSession().setAttribute("username", null);

        return modelAndView;
    }
}
