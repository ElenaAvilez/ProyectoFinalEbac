package com.proyectofinal.ebac.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombre;
    private Float costo;

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    @JsonBackReference
    private Proveedor proveedores;
}
