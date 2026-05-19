package com.proyectofinal.ebac.controller;

import com.proyectofinal.ebac.dto.Producto;
import com.proyectofinal.ebac.dto.Proveedor;
import com.proyectofinal.ebac.service.ProductoService;
import com.proyectofinal.ebac.service.ProveedorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProductoController productoController;

    //debe retornar listado de productos
    @Test
    void obtenerProductos() {
        int total = 4;
        List<Producto> productosEsperados = crearProductos(total);

        when(productoService.obtenerProductos()).thenReturn(productosEsperados);

        ResponseWrapper<List<Producto>> responseWrapper = productoController.obtenerProductos();
        ResponseEntity<List<Producto>> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Listado de productos disponibles", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(total, responseEntity.getBody().size());
        assertEquals(productosEsperados, responseEntity.getBody());

        verify(productoService, times(1)).obtenerProductos();
    }
    //obtenerProductoPorId cuando existe debe retornar producto
    @Test
    void obtenerProductoPorId() {
        Long id = 1L;
        Producto producto = crearProducto(id, 10L);

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.of(producto));

        ResponseWrapper<Producto> responseWrapper = productoController.obtenerProductoPorId(id);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Información de producto" + id, responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(producto, responseEntity.getBody());

        verify(productoService, times(1)).obtenerProductoPorId(id);
    }

    //cuando no existe  debe retornar Not Found
    @Test
    void obtenerProductoPorIdDos() {
        Long id = 99L;

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.empty());

        ResponseWrapper<Producto> responseWrapper = productoController.obtenerProductoPorId(id);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Información de producto" + id, responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(productoService, times(1)).obtenerProductoPorId(id);
    }
    //cuando idProveedor es null debe retornar BadRequest
    @Test
    void crearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setCosto(15000f);

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(null);
        producto.setProveedores(proveedor);

        ResponseWrapper<Producto> responseWrapper = productoController.crearProducto(producto);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertFalse(responseWrapper.isSuccess());
        assertEquals("idProveedor requerido", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(proveedorService, never()).obtenerPorId(anyLong());
        verify(productoService, never()).crearProducto(any(Producto.class));
    }
    //Cuando proveedor no existe debe de retornar bad request
    @Test
    void crearProductoDos() throws Exception {
        Long idProveedor = 50L;
        Producto producto = crearProducto(null, idProveedor);

        when(proveedorService.obtenerPorId(idProveedor)).thenReturn(Optional.empty());

        ResponseWrapper<Producto> responseWrapper = productoController.crearProducto(producto);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertFalse(responseWrapper.isSuccess());
        assertEquals("Proveedor " + idProveedor + " no existe", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(proveedorService, times(1)).obtenerPorId(idProveedor);
        verify(productoService, never()).crearProducto(any(Producto.class));
    }
    //Si es valido debe de crear el producto
    @Test
    void crearProductoTres() throws Exception {
        Long idProveedor = 10L;
        Producto productoEntrada = crearProducto(null, idProveedor);
        Producto productoCreado = crearProducto(1L, idProveedor);

        when(proveedorService.obtenerPorId(idProveedor)).thenReturn(Optional.of(crearProveedor(idProveedor)));
        when(productoService.crearProducto(productoEntrada)).thenReturn(productoCreado);

        ResponseWrapper<Producto> responseWrapper = productoController.crearProducto(productoEntrada);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Producto creado exitosamente", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productoCreado, responseEntity.getBody());

        verify(proveedorService, times(1)).obtenerPorId(idProveedor);
        verify(productoService, times(1)).crearProducto(productoEntrada);
    }


    @Test
    void putProductos() {
        Long id = 1L;

        Producto productoExistente = crearProducto(id, 10L);
        Producto productoActualizado = crearProducto(null, 10L);
        productoActualizado.setNombre("Producto actualizado");
        productoActualizado.setCosto(999f);

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.of(productoExistente));

        ResponseWrapper<Producto> responseWrapper = productoController.putProductos(id, productoActualizado);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Producto actualizado correctamente", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(id, responseEntity.getBody().getIdProducto());
        assertEquals("Producto actualizado", responseEntity.getBody().getNombre());
        assertEquals(999f, responseEntity.getBody().getCosto());

        verify(productoService, times(1)).obtenerProductoPorId(id);
        verify(productoService, times(1)).actualizarProductos(productoActualizado);
    }
    //cuando no existe
    @Test
    void putProductosDos() {
        Long id = 99L;
        Producto productoActualizado = crearProducto(null, 10L);

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.empty());

        ResponseWrapper<Producto> responseWrapper = productoController.putProductos(id, productoActualizado);
        ResponseEntity<Producto> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertFalse(responseWrapper.isSuccess());
        assertEquals("El producto indicado no existe", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(productoService, times(1)).obtenerProductoPorId(id);
        verify(productoService, never()).actualizarProductos(any(Producto.class));
    }

    @Test
    void eliminarProducto() {
        Long id = 1L;
        Producto producto = crearProducto(id, 20L);

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.of(producto));

        ResponseWrapper<Void> responseWrapper = productoController.eliminarProducto(id);
        ResponseEntity<Void> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertTrue(responseWrapper.isSuccess());
        assertEquals("Producto eliminado correctamente", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(productoService, times(1)).obtenerProductoPorId(id);
        verify(productoService, times(1)).eliminarProducto(id);
    }

    @Test
    void eliminarProductoDos() {
        Long id = 99L;

        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.empty());

        ResponseWrapper<Void> responseWrapper = productoController.eliminarProducto(id);
        ResponseEntity<Void> responseEntity = responseWrapper.getResponseEntity();

        assertNotNull(responseWrapper);
        assertFalse(responseWrapper.isSuccess());
        assertEquals("El id " + id + " no existe", responseWrapper.getMessage());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(productoService, times(1)).obtenerProductoPorId(id);
        verify(productoService, never()).eliminarProducto(anyLong());
    }

    private List<Producto> crearProductos(int total) {
        return IntStream.rangeClosed(1, total)
                .mapToObj(i -> crearProducto((long) i, (long) (100 + i)))
                .collect(Collectors.toList());
    }

    private Producto crearProducto(Long idProducto, Long idProveedor) {
        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setNombre("Producto " + (idProducto == null ? "nuevo" : idProducto));
        producto.setCosto(100f);
        producto.setProveedores(crearProveedor(idProveedor));
        return producto;
    }

    private Proveedor crearProveedor(Long idProveedor) {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProveedor);
        proveedor.setNombre("Proveedor " + idProveedor);
        proveedor.setDireccion("Direccion " + idProveedor);
        proveedor.setTelefono("55512345" + idProveedor);
        return proveedor;
    }
}