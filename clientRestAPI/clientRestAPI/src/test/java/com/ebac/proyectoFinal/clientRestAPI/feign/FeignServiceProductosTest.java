package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.Producto;
import com.ebac.proyectoFinal.clientRestAPI.dto.Proveedor;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FeignServiceProductosTest {

    public static FeignServiceProductos feignServiceProductos;

    @BeforeAll
    static void setUp(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.ebac.proyectoFinal.clientRestAPI.feign");
        context.refresh();

        FeignApiClientProductos feignApiClientProductos = (FeignApiClientProductos) context.getBean("feignApiClientProductos");
        String apiUrl = (String) context.getBean("apiUrl");

        feignServiceProductos = new FeignServiceProductos(feignApiClientProductos, apiUrl);
    }

    @Test
    void getproductos() {
        feignServiceProductos.getproductos().getResponseEntity().getBody().forEach(System.out::println);
    }


    @Test
    void getProductoById() {
        Producto producto = feignServiceProductos.getProductoById(1).getResponseEntity().getBody();
        log.info("Producto {}", producto);
    }

    @Test
    void createProducto() {

        Proveedor proveedor = Proveedor.builder()
                .idProveedor(1L)
                .build();

        Producto producto = Producto.builder()
                .nombre("Maletas")
                .costo(567.99f)
                .proveedores(proveedor)
                .build();
        ResponseWrapper<Producto> responseWrapper = feignServiceProductos.createProducto(producto);
        log.info("Producto creado: {} ", responseWrapper.getResponseEntity().getBody());
    }


    @Test
    void updateProducto() {
        Proveedor proveedor = Proveedor.builder()
                .idProveedor(1L)
                .build();

        Producto producto = Producto.builder()
                .nombre("Maletas Pelican")
                .costo(568.99f)
                .proveedores(proveedor)
                .build();

        Producto productoActualizado = feignServiceProductos
                .updateProducto(1, producto)
                .getResponseEntity()
                .getBody();
        log.info("Producto actualizado: {} ", productoActualizado);
    }


    @Test
    void deleteProducto() {
        int id = 7;
        feignServiceProductos.deleteProducto(id);
        log.info("Producto eliminado con id {} fue eliminado", id );
    }

}