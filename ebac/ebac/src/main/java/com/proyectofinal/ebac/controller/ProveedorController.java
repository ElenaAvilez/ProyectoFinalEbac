package com.proyectofinal.ebac.controller;

import com.proyectofinal.ebac.dto.Proveedor;
import com.proyectofinal.ebac.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;


    @GetMapping("/proveedores")
    public ResponseWrapper<List<Proveedor>> getProveedores(){

        List<Proveedor> proveedorList = proveedorService.obtenerTodos();
        ResponseEntity<List<Proveedor>> responseEntity = ResponseEntity.ok(proveedorList);

        return new ResponseWrapper<>(true, "Listado de proveedores", responseEntity);
    }

    @GetMapping("/proveedores/{id}")
    public ResponseWrapper<Proveedor> getProveedoresById(@PathVariable Long id){
        System.out.println("Id recibido: " + id);

        Optional<Proveedor> proveedoresOptional = proveedorService.obtenerPorId(id);

        if (proveedoresOptional.isPresent()){
            ResponseEntity<Proveedor> proveedorResponseEntity = ResponseEntity.ok(proveedoresOptional.get());
            return new ResponseWrapper<>(true, "Información del proveedor" + id, proveedorResponseEntity);
        } else {
            ResponseEntity<Proveedor> proveedorResponseEntity = ResponseEntity.notFound().build();
            return  new ResponseWrapper<>(false, "Información del proveedor" + id, proveedorResponseEntity);
        }

    }

    @PostMapping("/proveedores")
    public ResponseWrapper<Proveedor> postProveedores(@RequestBody Proveedor proveedor){

        try{
            Proveedor proveedorCreado = proveedorService.creandoProveedores(proveedor);
            ResponseEntity<Proveedor> responseEntity = ResponseEntity.created(new URI("http://localhost/proveedores")).body(proveedorCreado);
            return new ResponseWrapper<>(true, "Proveedor creado exitosamente", responseEntity);
        } catch (Exception exception){
            ResponseEntity<Proveedor> responseEntity = ResponseEntity.badRequest().build();
            return new ResponseWrapper<>(false, exception.getMessage(), responseEntity);
        }
    }

    @PutMapping("/proveedores/{id}")
    public ResponseWrapper<Proveedor> putProveedores(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado){

        Optional<Proveedor> proveedoresOptional = proveedorService.obtenerPorId(id);

        if (proveedoresOptional.isPresent()){
            proveedorActualizado.setIdProveedor(proveedoresOptional.get().getIdProveedor());
            proveedorService.actualizandoProveedores(proveedorActualizado);

            ResponseEntity<Proveedor> responseEntity = ResponseEntity.ok(proveedorActualizado);

            return new ResponseWrapper<>(true, "Proveedor actualizado correctamente", responseEntity);
        }else {
            ResponseEntity<Proveedor> responseEntity = ResponseEntity.notFound().build();
            return new ResponseWrapper<>(false, "El proveedor indicado no existe", responseEntity);

        }
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseWrapper<Void> eliminadoProveedor(@PathVariable Long id){
        proveedorService.eliminarProveedores(id);

        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
        return new ResponseWrapper<>(true, "Proveedor eliminado correctamente", responseEntity);
    }
}

