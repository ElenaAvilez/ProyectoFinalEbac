package com.proyectofinal.ebac.service;

import com.proyectofinal.ebac.dto.Producto;
import com.proyectofinal.ebac.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProveedorService proveedorService;

    public Producto crearProducto(Producto productos) throws Exception{
        return  productoRepository.save(productos);
    }

    public Optional<Producto> obtenerProductoPorId(Long idProducto){
        return productoRepository.findById(idProducto);
    }
    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public void actualizarProductos(Producto producto){
        productoRepository.save(producto);

    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);

    }

}
