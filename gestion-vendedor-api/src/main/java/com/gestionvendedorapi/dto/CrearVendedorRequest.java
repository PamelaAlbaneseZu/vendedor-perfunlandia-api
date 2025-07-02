package com.gestionvendedorapi.dto;

import lombok.Data;

// Estos son los datos que se reciben para crear un vendedor
@Data
public class CrearVendedorRequest
{
    private Integer idUsuario;
    private Integer idSucursal;
    
}
