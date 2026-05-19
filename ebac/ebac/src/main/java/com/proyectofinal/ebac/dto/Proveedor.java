package com.proyectofinal.ebac.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    @OneToMany(mappedBy = "proveedores", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Producto> productos = new ArrayList<>();

}
