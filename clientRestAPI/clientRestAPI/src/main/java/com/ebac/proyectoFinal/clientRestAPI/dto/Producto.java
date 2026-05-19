package com.ebac.proyectoFinal.clientRestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Producto {

    private Long idProducto;
    private String nombre;
    private Float costo;
    private Proveedor proveedores;

}
