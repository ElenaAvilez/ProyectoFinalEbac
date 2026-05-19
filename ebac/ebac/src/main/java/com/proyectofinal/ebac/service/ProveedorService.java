package com.proyectofinal.ebac.service;

import com.proyectofinal.ebac.dto.Proveedor;
import com.proyectofinal.ebac.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public Proveedor creandoProveedores(Proveedor proveedores){
        return proveedorRepository.save(proveedores);

    }

    public Optional<Proveedor> obtenerPorId(Long idProveedores){

        return proveedorRepository.findById(idProveedores);
    }

    public List<Proveedor> obtenerTodos(){
        return proveedorRepository.findAll();
    }

    public void actualizandoProveedores(Proveedor proveedores){
        proveedorRepository.save(proveedores);
    }
    public void eliminarProveedores(Long id){

        proveedorRepository.deleteById(id);
    }
}
