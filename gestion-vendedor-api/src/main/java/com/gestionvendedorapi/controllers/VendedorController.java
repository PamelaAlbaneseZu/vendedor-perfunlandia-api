package com.gestionvendedorapi.controllers;

// Clase que define los endpoints HTTP que presenta la informacion en (postman)
// utilizando los metodos definidos en services. Tambien se muestran las rutas para las consultas

import com.gestionvendedorapi.dto.*;
import com.gestionvendedorapi.models.VendedorDetalle;
import com.gestionvendedorapi.services.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("/api/vendedores")
@RequiredArgsConstructor
@Tag(name = "Gestión de Vendedores", description = "API para gestionar vendedores del sistema")
public class VendedorController {

    private final VendedorService vendedorService;

    // POST /vendedores
    @PostMapping
    public ResponseEntity<VendedorDetalle> crear(@RequestBody CrearVendedorRequest request) {
        VendedorDetalle nuevo = vendedorService.crearVendedor(request);
        return ResponseEntity.ok(nuevo);
    }

    // GET /vendedores/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> obtenerPorId(@PathVariable Integer id) {
        VendedorDTO dto = vendedorService.obtenerVendedor(id);
        return ResponseEntity.ok(dto);
    }

    // PUT /vendedores
    @PutMapping
    public ResponseEntity<VendedorDTO> actualizarSucursal(@RequestBody ActualizarSucursalRequest request) {
        VendedorDTO actualizado = vendedorService.actualizarSucursal(
                request.getIdVendedor(), request.getNuevaSucursal());
        return ResponseEntity.ok(actualizado);
    }

    // GET /vendedores/sucursal/{idSucursal}
    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<VendedorDTO>> listarPorSucursal(@PathVariable Integer idSucursal) {
        List<VendedorDTO> lista = vendedorService.listarPorSucursal(idSucursal);
        return ResponseEntity.ok(lista);
    }

    // Métodos con HATEOAS que apuntan al API Gateway (puerto 8888)
    
    @PostMapping("/hateoas")
    @Operation(summary = "Crear vendedor con HATEOAS", description = "Crea un nuevo vendedor con enlaces HATEOAS apuntando al API Gateway")
    public ResponseEntity<EntityModel<VendedorDetalle>> crearConHateoas(@RequestBody CrearVendedorRequest request) {
        VendedorDetalle nuevo = vendedorService.crearVendedor(request);
        
        EntityModel<VendedorDetalle> resource = EntityModel.of(nuevo);
        resource.add(Link.of("http://localhost:8888/api/vendedores/" + nuevo.getIdVendedor(), "self"));
        resource.add(Link.of("http://localhost:8888/api/vendedores", "collection"));
        resource.add(Link.of("http://localhost:8888/api/vendedores/sucursal/" + nuevo.getIdSucursal(), "sucursal"));
        
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/hateoas/{id}")
    @Operation(summary = "Obtener vendedor por ID con HATEOAS", description = "Obtiene la información de un vendedor específico con enlaces HATEOAS apuntando al API Gateway")
    public ResponseEntity<EntityModel<VendedorDTO>> obtenerPorIdConHateoas(@Parameter(description = "ID del vendedor") @PathVariable Integer id) {
        VendedorDTO dto = vendedorService.obtenerVendedor(id);
        
        EntityModel<VendedorDTO> resource = EntityModel.of(dto);
        resource.add(Link.of("http://localhost:8888/api/vendedores/" + id, "self"));
        resource.add(Link.of("http://localhost:8888/api/vendedores", "collection"));
        resource.add(Link.of("http://localhost:8888/api/vendedores/sucursal/" + dto.getIdSucursal(), "sucursal"));
        
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/hateoas")
    @Operation(summary = "Actualizar sucursal del vendedor con HATEOAS", description = "Actualiza la sucursal asignada a un vendedor con enlaces HATEOAS apuntando al API Gateway")
    public ResponseEntity<EntityModel<VendedorDTO>> actualizarSucursalConHateoas(@RequestBody ActualizarSucursalRequest request) {
        VendedorDTO actualizado = vendedorService.actualizarSucursal(
                request.getIdVendedor(), request.getNuevaSucursal());
        
        EntityModel<VendedorDTO> resource = EntityModel.of(actualizado);
        resource.add(Link.of("http://localhost:8888/api/vendedores/" + actualizado.getIdVendedor(), "self"));
        resource.add(Link.of("http://localhost:8888/api/vendedores", "collection"));
        resource.add(Link.of("http://localhost:8888/api/vendedores/sucursal/" + actualizado.getIdSucursal(), "sucursal"));
        
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/hateoas/sucursal/{idSucursal}")
    @Operation(summary = "Listar vendedores por sucursal con HATEOAS", description = "Obtiene la lista de vendedores asignados a una sucursal específica con enlaces HATEOAS apuntando al API Gateway")
    public ResponseEntity<List<EntityModel<VendedorDTO>>> listarPorSucursalConHateoas(@Parameter(description = "ID de la sucursal") @PathVariable Integer idSucursal) {
        List<VendedorDTO> lista = vendedorService.listarPorSucursal(idSucursal);
        
        List<EntityModel<VendedorDTO>> resources = lista.stream()
            .map(dto -> {
                EntityModel<VendedorDTO> resource = EntityModel.of(dto);
                resource.add(Link.of("http://localhost:8888/api/vendedores/" + dto.getIdVendedor(), "self"));
                resource.add(Link.of("http://localhost:8888/api/vendedores", "collection"));
                resource.add(Link.of("http://localhost:8888/api/vendedores/sucursal/" + dto.getIdSucursal(), "sucursal"));
                return resource;
            })
            .toList();
        
        return ResponseEntity.ok(resources);
    }
}