package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.Proveedor;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class FeignServiceProveedoresTest {

    public static FeignServiceProveedores feignServiceProveedores;

    @BeforeAll
    static void setUp(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.ebac.proyectoFinal.clientRestAPI.feign");
        context.refresh();

        FeignApiClientProveedores feignApiClientProveedores = (FeignApiClientProveedores) context.getBean("feignApiClientProveedores");
        String apiUrl = (String) context.getBean("apiUrl");

        feignServiceProveedores = new FeignServiceProveedores(feignApiClientProveedores, apiUrl);
    }

    @Test
    void testGetProveedores() {

        feignServiceProveedores.getProveedores().getResponseEntity().getBody().forEach(System.out::println);
    }

    @Test
    void testGetProveedorById() {
        Proveedor proveedor = feignServiceProveedores.getProveedorById(3).getResponseEntity().getBody();
        log.info("Proveedor: {}", proveedor);
    }



    @Test
    void testCreateProveedor() {

        Proveedor proveedor = Proveedor.builder()
                .nombre("Karen2")
                .direccion("Tangamandapio")
                .telefono("557685839393")
                .build();
        ResponseWrapper<Proveedor> responseWrapper = feignServiceProveedores.createProveedor(proveedor);
        log.info("Proveedor creado: {}", responseWrapper.getResponseEntity().getBody());
    }

    @Test
    void testUpdateProveedor() {
        Proveedor proveedor = Proveedor.builder()
                .nombre("Elena")
                .direccion("Tangamandapio2")
                .telefono("55768111111")
                .build();
        Proveedor proveedorActualizado = feignServiceProveedores.updateProveedor(2,proveedor).getResponseEntity().getBody();
        log.info("Proveedor actualizado: {}", proveedorActualizado);
    }


    @Test
    void testDeleteProveedor() {

        int id = 6;
        feignServiceProveedores.deleteProveedor(id);
        log.info("Proveedor con id {} fue eliminado ", id );
    }

}