package com.webapp.proyectofinal.controller;

import com.webapp.proyectofinal.dto.Proveedor;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import com.webapp.proyectofinal.feign.FeignServiceProveedores;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    FeignServiceProveedores feignServiceProveedores;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Object index(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info("Ingresando a página principal");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        ResponseWrapper<List<Proveedor>> proveedoresResponse = feignServiceProveedores.getProveedores();
        List<Proveedor> provedores = new ArrayList<>();
        if (proveedoresResponse.isSuccess()) {
            provedores = proveedoresResponse.getResponseEntity().getBody();
        }

        model.addAttribute("numProvedores", provedores.size());
        model.addAttribute("provedores", provedores);

        return modelAndView;
    }
}
