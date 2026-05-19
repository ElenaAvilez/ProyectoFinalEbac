package com.ebac.proyectoFinal.clientRestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Proveedor {
    private Long idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Producto> productos = new ArrayList<>();
}
