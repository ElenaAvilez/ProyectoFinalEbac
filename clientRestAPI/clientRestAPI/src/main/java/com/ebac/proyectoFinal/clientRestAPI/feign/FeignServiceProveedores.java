package com.ebac.proyectoFinal.clientRestAPI.feign;

import com.ebac.proyectoFinal.clientRestAPI.dto.Proveedor;
import com.ebac.proyectoFinal.clientRestAPI.dto.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FeignServiceProveedores {

    public final FeignApiClientProveedores feignApiClientProveedores;
    public final String apiUrl;

    public FeignServiceProveedores(FeignApiClientProveedores feignApiClientProveedores, String apiUrl) {
        this.feignApiClientProveedores = feignApiClientProveedores;
        this.apiUrl = apiUrl;
    }

    public ResponseWrapper<List<Proveedor>> getProveedores(){
        log.info("Obteniendo usuarios desde la api {}", apiUrl);
        return feignApiClientProveedores.getProveedores();
    }

    public ResponseWrapper<Proveedor> getProveedorById(int id){
        log.info("Obteniendo proveedor con id {}", id);
        try{
            return feignApiClientProveedores.getProveedorById(id);
        } catch (Exception e){
            return null;
        }
    }

    public ResponseWrapper<Proveedor> createProveedor (Proveedor proveedor){
        log.info("Creando proveedor {}", proveedor);
        return feignApiClientProveedores.createProveedor(proveedor);
    }
    
    public ResponseWrapper<Proveedor> updateProveedor(int id, Proveedor proveedor){
        log.info("Actualizando información de proveedor {}", proveedor.getNombre());
        return feignApiClientProveedores.updateProveedor(proveedor, id);
    }
    
    public void deleteProveedor(int id){
        log.info("Eliminando proveedor con el id {}", id);
        feignApiClientProveedores.deleteProveedor(id);
    }
}
