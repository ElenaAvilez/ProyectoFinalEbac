package com.webapp.proyectofinal.feign;


import com.webapp.proyectofinal.dto.Producto;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FeignServiceProductos {

    public final FeignApiClientProductos feignApiClientProductos;
    public final String apiUrl;

    public FeignServiceProductos(FeignApiClientProductos feignApiClientProductos, String apiUrl) {
        this.feignApiClientProductos = feignApiClientProductos;
        this.apiUrl = apiUrl;
    }


    public ResponseWrapper<List<Producto>> getproductos() {
        log.info("Obteniendo productos desde la api {}", apiUrl);
        return feignApiClientProductos.getProductos();
    }

    public ResponseWrapper<Producto> getProductoById(int id) {
        log.info("Obteniendo producto con id {}", id);
        try {
            return feignApiClientProductos.getProductosById(id);
        } catch (Exception e){
            return  null;
        }
    }

    public ResponseWrapper<Producto> createProducto(Producto producto) {
        log.info("Creando Producto {}", producto);
        return feignApiClientProductos.createProducto(producto);
    }

    public ResponseWrapper<Producto> updateProducto(int id, Producto producto) {
        log.info("Actualizando numero {}", producto.getNombre());
        return feignApiClientProductos.updateProducto(producto, id);
    }

    public void deleteProducto(int id) {
        log.info("Eliminando producto con id {}", id);
        feignApiClientProductos.deleteProducto(id);
    }

}
