package com.gestionvendedorapi.services;

// Clase donde se implementa la logica de negocio
// creando metodos que se utilizan desde controlador

import com.gestionvendedorapi.dto.*;
import com.gestionvendedorapi.models.VendedorDetalle;
import com.gestionvendedorapi.repositories.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final RestTemplate restTemplate;

    // URLs de las APIs externas
    private static final String URL_USUARIO = "http://localhost:8082/api/usuarios/";
    private static final String URL_SUCURSAL = "http://localhost:8083/api/sucursales/";


    // Este metodo crea un nuevo vendedor validando usuario y sucursal
    public VendedorDetalle crearVendedor(CrearVendedorRequest request)
    {
        UsuarioResponse usuario = getUsuario(request.getIdUsuario());

        if (!"VENDEDOR".equalsIgnoreCase(usuario.getRol()))
        {
            throw new RuntimeException("El usuario no tiene el rol VENDEDOR.");
        }

        if (!sucursalExiste(request.getIdSucursal()))
        {
            throw new RuntimeException("Sucursal no encontrada.");
        }

        VendedorDetalle vendedor = new VendedorDetalle();
        vendedor.setIdUsuario(request.getIdUsuario());
        vendedor.setIdSucursal(request.getIdSucursal());

        return vendedorRepository.save(vendedor);
    }

    // Este metodo obtiene un vendedor por su ID, incluye nombre del usuario
    public VendedorDTO obtenerVendedor(Integer id)
    {
        VendedorDetalle detalle = vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        UsuarioResponse usuario = getUsuario(detalle.getIdUsuario());

        VendedorDTO dto = new VendedorDTO();
        dto.setIdVendedor(detalle.getIdVendedor());
        dto.setIdUsuario(detalle.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreCompleto());
        dto.setIdSucursal(detalle.getIdSucursal());

        return dto;
    }

    // Este metodo lista todos los vendedores de una sucursal
    public List<VendedorDTO> listarPorSucursal(Integer idSucursal)
    {
        return vendedorRepository.findByIdSucursal(idSucursal).stream().map(v ->
        {
            UsuarioResponse usuario = getUsuario(v.getIdUsuario());

            VendedorDTO dto = new VendedorDTO();
            dto.setIdVendedor(v.getIdVendedor());
            dto.setIdUsuario(v.getIdUsuario());
            dto.setNombreUsuario(usuario.getNombreCompleto());
            dto.setIdSucursal(v.getIdSucursal());

            return dto;
        }).collect(Collectors.toList());
    }

    // Este metodo es para actualizar la sucursal de un vendedor
    public VendedorDTO actualizarSucursal(Integer idVendedor, Integer nuevaSucursal)
    {
        VendedorDetalle vendedor = vendedorRepository.findById(idVendedor)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado."));

        if (!sucursalExiste(nuevaSucursal))
        {
            throw new RuntimeException("Sucursal no encontrada.");
        }

        vendedor.setIdSucursal(nuevaSucursal);
        vendedorRepository.save(vendedor);

        UsuarioResponse usuario = getUsuario(vendedor.getIdUsuario());

        VendedorDTO dto = new VendedorDTO();
        dto.setIdVendedor(vendedor.getIdVendedor());
        dto.setIdUsuario(vendedor.getIdUsuario());
        dto.setNombreUsuario(usuario.getNombreCompleto());
        dto.setIdSucursal(vendedor.getIdSucursal());

        return dto;
    }

    // Este metodo llama a la API de usuarios
    private UsuarioResponse getUsuario(Integer idUsuario)
    {
        ResponseEntity<UsuarioResponse> response = restTemplate.getForEntity(
                URL_USUARIO + idUsuario, UsuarioResponse.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null)
        {
            throw new RuntimeException("Usuario no encontrado.");
        }

        return response.getBody();
    }

    // Este metodo llama a la API de localización para validar sucursal
    private boolean sucursalExiste(Integer idSucursal) {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(
                URL_SUCURSAL + idSucursal + "/existe", Boolean.class);

        Boolean body = response.getBody();
        return body != null && body;
    }
}