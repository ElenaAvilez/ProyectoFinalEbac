package com.webapp.proyectofinal.feign;


import com.webapp.proyectofinal.dto.Producto;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface FeignApiClientProductos {

    @RequestLine("GET /productos")
    ResponseWrapper<List<Producto>> getProductos();

    @RequestLine("GET /productos/{id}")
    ResponseWrapper<Producto> getProductosById(@Param("id") int id);

    @RequestLine("POST /productos")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Producto> createProducto(Producto producto);

    @RequestLine("PUT /productos/{id}")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Producto> updateProducto(Producto producto, @Param("id") int id);

    @RequestLine("DELETE /productos/{id}")
    void deleteProducto(@Param("id") int id);
}
