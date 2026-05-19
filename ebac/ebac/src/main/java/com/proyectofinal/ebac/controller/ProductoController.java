package com.proyectofinal.ebac.controller;

import com.proyectofinal.ebac.dto.Producto;
import com.proyectofinal.ebac.dto.Proveedor;
import com.proyectofinal.ebac.service.ProductoService;
import com.proyectofinal.ebac.service.ProveedorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/productos")
    public ResponseWrapper<List<Producto>> obtenerProductos(){
        List<Producto> productos = productoService.obtenerProductos();
        ResponseEntity<List<Producto>> responseEntity = ResponseEntity.ok(productos);

        return new ResponseWrapper<>(true, "Listado de productos disponibles", responseEntity);
    }

    @GetMapping("/productos/{id}")
    public ResponseWrapper<Producto> obtenerProductoPorId(@PathVariable Long id){

        Optional<Producto> productoOptional = productoService.obtenerProductoPorId(id);
        ResponseEntity<Producto> responseEntity = productoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        return new ResponseWrapper<>(true, "Información de producto" + id, responseEntity);
    }

    @PostMapping("/productos")
    public ResponseWrapper<Producto> crearProducto(@RequestBody Producto producto){
        try {
            Long idProveedor = producto.getProveedores().getIdProveedor();
            if(idProveedor == null){
                ResponseEntity<Producto> responseEntity = ResponseEntity.badRequest().build();
                return new ResponseWrapper<>(false, "idProveedor requerido", responseEntity);
            }
            if (proveedorService.obtenerPorId(idProveedor).isEmpty()){
                ResponseEntity<Producto> responseEntity = ResponseEntity.badRequest().build();
                return new ResponseWrapper<>(false, "Proveedor " + idProveedor + " no existe", responseEntity);
            }

            Producto productoCreado = productoService.crearProducto(producto);
            ResponseEntity<Producto> responseEntity = ResponseEntity.created(new URI("http://localhost/productos")).body(productoCreado);
            return new ResponseWrapper<>(true, "Producto creado exitosamente", responseEntity);
        } catch (Exception e){
            ResponseEntity<Producto> responseEntity = ResponseEntity.badRequest().build();
            return new ResponseWrapper<>(false, e.getMessage(), responseEntity);
        }
    }
    @PutMapping("/productos/{id}")
    public ResponseWrapper<Producto> putProductos(@PathVariable Long id, @RequestBody Producto productoActualizado){

        Optional<Producto> productoOptional = productoService.obtenerProductoPorId(id);

        if (productoOptional.isPresent()){
            productoActualizado.setIdProducto(productoOptional.get().getIdProducto());
            productoService.actualizarProductos(productoActualizado);

            ResponseEntity<Producto> responseEntity = ResponseEntity.ok(productoActualizado);

            return new ResponseWrapper<>(true, "Producto actualizado correctamente", responseEntity);
        }else {
            ResponseEntity<Producto> responseEntity = ResponseEntity.notFound().build();
            return new ResponseWrapper<>(false, "El producto indicado no existe", responseEntity);

        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseWrapper<Void> eliminarProducto(@PathVariable Long id){

        if(productoService.obtenerProductoPorId(id).isEmpty()){
            ResponseEntity<Void> responseEntity = ResponseEntity.notFound().build();
            return new ResponseWrapper<>(false, "El id "+ id + " no existe", responseEntity);
        }

        productoService.eliminarProducto(id);

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
        return new ResponseWrapper<>(true, "Producto eliminado correctamente", responseEntity);
    }
}
