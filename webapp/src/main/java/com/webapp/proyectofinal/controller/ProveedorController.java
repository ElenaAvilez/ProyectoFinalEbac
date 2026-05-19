package com.webapp.proyectofinal.controller;


import com.webapp.proyectofinal.dto.Producto;
import com.webapp.proyectofinal.dto.Proveedor;
import com.webapp.proyectofinal.dto.ResponseWrapper;
import com.webapp.proyectofinal.feign.FeignServiceProveedores;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class ProveedorController {

    @Autowired
    FeignServiceProveedores feignServiceProveedores;


    @RequestMapping(value = "/proveedor", method = RequestMethod.GET)
    public Object informacionProveedor(HttpServletRequest request, HttpServletResponse response, Model model) {
        String idProveedor = request.getParameter("idProveedor");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("proveedor");

        Proveedor proveedor = Proveedor.creaProveedorVacio();

        if (!Objects.isNull(idProveedor) && !idProveedor.isEmpty()) {
            ResponseWrapper<Proveedor> proveedorResponse = feignServiceProveedores.getProveedorById(Integer.parseInt(idProveedor));
            if (proveedorResponse.isSuccess()) {
                proveedor = proveedorResponse.getResponseEntity().getBody();
            }
        }

        model.addAttribute("proveedor", proveedor);
        return modelAndView;
    }

    @RequestMapping(value = "/formulario-proveedor", method = RequestMethod.GET)
    public Object formularioProveedor(HttpServletRequest request, HttpServletResponse response, Model model) {
        String idProveedor = request.getParameter("idProveedor");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formulario-proveedor");

        Proveedor proveedor = Proveedor.creaProveedorVacio();
        List<Producto> productos = new ArrayList<>(proveedor.getProductos());
        model.addAttribute("propositoFormulario", "Crear proveedor");

        if (!Objects.isNull(idProveedor) && !idProveedor.isEmpty()) {
            ResponseWrapper<Proveedor> proveedorResponse = feignServiceProveedores.getProveedorById(Integer.parseInt(idProveedor));
            if (proveedorResponse != null && proveedorResponse.isSuccess()) {
                proveedor = proveedorResponse.getResponseEntity().getBody();
                // Solo extraemos el primer telefono (En caso de tener)
                // Actividad sugerida: Generar funcionalidad para retornar todos los telefonos en caso de tener mas de 1 y generar el formulario correspondiente
                if (proveedor.getProductos() != null && !proveedor.getProductos().isEmpty()) {
                    productos = new ArrayList<>(proveedor.getProductos());
                }else {
                    productos = new ArrayList<>();
                    productos.add(Producto.builder().nombre("").costo(0f).build());
                }
                model.addAttribute("propositoFormulario", "Actualizar proveedor");
            }
        }

        model.addAttribute("proveedor", proveedor);
        model.addAttribute("producto", productos);
        return modelAndView;
    }

    @RequestMapping(value = "/guardar-proveedor", method = RequestMethod.POST)
    public ResponseEntity<Response> saveProveedorConfiguration(HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.OK;
        Response response = null;

        try {
            String proveedorId = request.getParameter("FormProveedorId");
            String proveedorNombre = request.getParameter("FormProveedorNombre");
            String proveedorDireccion = request.getParameter("FormProveedorDireccion");
            String proveedorTelefono = request.getParameter("FormProveedorTelefono");

            String [] productoNombre = request.getParameterValues("FormProductoNombre");
            String [] productoCosto = request.getParameterValues("FormProductoCosto");
            String [] productoId = request.getParameterValues("FrmProductoId");


            ErrorResponse errorResponse = validarProveedor(
                    proveedorId,
                    proveedorNombre,
                    proveedorDireccion,
                    proveedorTelefono,
                    productoNombre,
                    productoCosto
            );

            if (!errorResponse.getMessages().isEmpty()) {
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            Proveedor.ProveedorBuilder proveedorBuilder = Proveedor.builder();

            if (!proveedorId.equals("0")) {
                proveedorBuilder = proveedorBuilder.idProveedor(Long.parseLong(proveedorId));
            }

            List<Producto> productos = construirProductos(productoId, productoNombre, productoCosto);


            //Generamos el usuario
            Proveedor proveedor = proveedorBuilder
                    .nombre(proveedorNombre)
                    .direccion(proveedorDireccion)
                    .telefono(proveedorTelefono)
                    .productos(productos)
                    .build();

            ResponseWrapper<Proveedor> proveedorResponse;

            if (proveedor.getIdProveedor() != null && proveedor.getIdProveedor() > 0) {
                proveedorResponse = feignServiceProveedores.updateProveedor(
                        proveedor.getIdProveedor().intValue(),
                        proveedor
                );
            } else {
                proveedorResponse = feignServiceProveedores.createProveedor(proveedor);
            }
            if (proveedorResponse == null || !proveedorResponse.isSuccess()) {
                ErrorResponse backendError = new ErrorResponse();
                backendError.addMessage(proveedorResponse != null ? proveedorResponse.getMessage(): "No fue posible gurdar el proveedor");
                response = errorResponse;

                if (proveedorResponse != null && proveedorResponse.getResponseEntity() != null) {
                    statusCode = HttpStatus.resolve(proveedorResponse.getResponseEntity().getStatusCodeValue());
                    if (statusCode == null) {
                        statusCode = HttpStatus.BAD_REQUEST;
                    }
                } else {
                    statusCode = HttpStatus.BAD_REQUEST;
                }
            }
        } catch(Exception e) {
            log.error("Error al guardar proveedor", e);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.addMessage("Ocurrió un error al guardar el proveedor");
            response = errorResponse;
            statusCode = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(response, statusCode);
    }

    private ErrorResponse validarProveedor(
            String proveedorId,
            String proveedorNombre,
            String proveedorDireccion,
            String proveedorTelefono,
            String[] productosNombre,
            String[] productosCosto
    ) {
        ErrorResponse errorResponse = new ErrorResponse();

        if (proveedorId == null || proveedorId.isBlank()) {
            errorResponse.addMessage("Id de proveedor requerido");
        } else {
            try {
                Long.parseLong(proveedorId);
            } catch (NumberFormatException e) {
                errorResponse.addMessage("Id de proveedor inválido");
            }
        }

        if (proveedorNombre == null || proveedorNombre.isBlank()) {
            errorResponse.addMessage("Nombre de proveedor requerido");
        }

        if (proveedorDireccion == null || proveedorDireccion.isBlank()) {
            errorResponse.addMessage("Dirección de proveedor requerida");
        }

        if (proveedorTelefono == null || proveedorTelefono.isBlank()) {
            errorResponse.addMessage("Teléfono de proveedor requerido");
        }

        if (productosNombre == null || productosNombre.length == 0) {
            errorResponse.addMessage("Debe capturar al menos un producto");
        }

        if (productosCosto == null || productosCosto.length == 0) {
            errorResponse.addMessage("Debe capturar el costo del producto");
        }

        if (productosNombre != null && productosCosto != null && productosNombre.length != productosCosto.length) {
            errorResponse.addMessage("La información de productos está incompleta");
        }

        if (productosNombre != null && productosCosto != null) {
            for (int i = 0; i < productosNombre.length; i++) {
                if (productosNombre[i] == null || productosNombre[i].isBlank()) {
                    errorResponse.addMessage("Nombre requerido para el producto " + (i + 1));
                }

                if (productosCosto[i] == null || productosCosto[i].isBlank()) {
                    errorResponse.addMessage("Costo requerido para el producto " + (i + 1));
                } else {
                    try {
                        Float costo = Float.valueOf(productosCosto[i]);
                        if (costo <= 0) {
                            errorResponse.addMessage("El costo del producto " + (i + 1) + " debe ser mayor a 0");
                        }
                    } catch (NumberFormatException e) {
                        errorResponse.addMessage("Costo inválido para el producto " + (i + 1));
                    }
                }
            }
        }

        return errorResponse;
    }

    private List<Producto> construirProductos(
            String[] productosId,
            String[] productosNombre,
            String[] productosCosto
    ) {
        List<Producto> productos = new ArrayList<>();

        for (int i = 0; i < productosNombre.length; i++) {
            Producto.ProductoBuilder productoBuilder = Producto.builder()
                    .nombre(productosNombre[i].trim())
                    .costo(Float.valueOf(productosCosto[i]));

            if (productosId != null && productosId.length > i) {
                String idProducto = productosId[i];

                if (idProducto != null && !idProducto.isBlank() && !idProducto.equals("0")) {
                    productoBuilder.idProducto(Long.parseLong(idProducto));
                }
            }

            productos.add(productoBuilder.build());
        }

        return productos;
    }

}
