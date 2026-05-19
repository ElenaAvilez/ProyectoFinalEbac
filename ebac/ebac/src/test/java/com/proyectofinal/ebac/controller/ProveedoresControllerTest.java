package com.proyectofinal.ebac.controller;

import com.proyectofinal.ebac.dto.Producto;
import com.proyectofinal.ebac.dto.Proveedor;
import com.proyectofinal.ebac.repository.ProveedorRepository;
import com.proyectofinal.ebac.service.ProveedorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedoresControllerTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void creandoProveedores() {
        Proveedor proveedor = crearProveedor(1L);
        Producto producto = crearProducto(1L);
        proveedor.setProductos(List.of(producto));

        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        Proveedor resultado = proveedorService.creandoProveedores(proveedor);

        assertNotNull(resultado);
        assertEquals(proveedor, resultado);

        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    void obtenerPorId() {
        Long id = 1L;
        Proveedor proveedor = crearProveedor(id);

        when(proveedorRepository.findById(id)).thenReturn(Optional.of(proveedor));

        Optional<Proveedor> resultado = proveedorService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(proveedor, resultado.get());

        verify(proveedorRepository, times(1)).findById(id);
    }

    @Test
    void obtenerPorIdDos() {
        Long id = 99L;

        when(proveedorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Proveedor> resultado = proveedorService.obtenerPorId(id);

        assertTrue(resultado.isEmpty());

        verify(proveedorRepository, times(1)).findById(id);
    }

    @Test
    void obtenerTodos() {
        List<Proveedor> proveedores = List.of(
                crearProveedor(1L),
                crearProveedor(2L),
                crearProveedor(3L)
        );

        when(proveedorRepository.findAll()).thenReturn(proveedores);

        List<Proveedor> resultado = proveedorService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals(proveedores, resultado);

        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    void actualizandoProveedores() {
        Proveedor proveedor = crearProveedor(1L);
        Producto producto = crearProducto(10L);
        proveedor.setProductos(List.of(producto));

        proveedorService.actualizandoProveedores(proveedor);

        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    void eliminarProveedores_() {
        Long id = 1L;

        proveedorService.eliminarProveedores(id);

        verify(proveedorRepository, times(1)).deleteById(id);
    }

    @Test
    void creandoProveedoresDos() {
        Proveedor proveedor = crearProveedor(1L);
        proveedor.setProductos(null);

        when(proveedorRepository.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Proveedor resultado = proveedorService.creandoProveedores(proveedor);

        assertNotNull(resultado);
        assertSame(proveedor, resultado);

        verify(proveedorRepository, times(1)).save(proveedor);
    }

    private Proveedor crearProveedor(Long id) {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(id);
        proveedor.setNombre("Proveedor " + id);
        proveedor.setDireccion("Direccion " + id);
        proveedor.setTelefono("55512345" + id);
        return proveedor;
    }

    private Producto crearProducto(Long id) {
        Producto producto = new Producto();
        producto.setIdProducto(id);
        producto.setNombre("Producto " + id);
        producto.setCosto(100f);
        return producto;
    }
}