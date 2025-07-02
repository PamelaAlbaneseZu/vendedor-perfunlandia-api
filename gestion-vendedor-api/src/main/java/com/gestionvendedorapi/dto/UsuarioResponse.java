package com.gestionvendedorapi.dto;

import lombok.Data;

// Esto representa los datos del usuario que obtenemos desde API Gestion Usuario
@Data
public class UsuarioResponse
{
    private Integer idUsuario;
    private String nombreCompleto;
    private String rol;

}