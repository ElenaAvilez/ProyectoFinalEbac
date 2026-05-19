package com.proyectofinal.ebac.repository;

import com.proyectofinal.ebac.dto.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
