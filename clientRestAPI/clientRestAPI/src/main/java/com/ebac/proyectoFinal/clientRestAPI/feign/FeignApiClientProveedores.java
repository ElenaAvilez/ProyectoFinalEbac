package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.Proveedor;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface FeignApiClientProveedores {

    @RequestLine("GET /proveedores")
    ResponseWrapper<List<Proveedor>> getProveedores();

    @RequestLine("GET /proveedores/{id}")
    ResponseWrapper<Proveedor> getProveedorById(@Param("id") int id);

    @RequestLine("POST /proveedores")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Proveedor> createProveedor(Proveedor proveedor);

    @RequestLine("PUT /proveedores/{id}")
    @Headers({"Content-Type: application/json"})
    ResponseWrapper<Proveedor> updateProveedor(Proveedor proveedor, @Param("id") int id);

    @RequestLine("DELETE /proveedores/{id}")
    void deleteProveedor (@Param("id") int id);
}
