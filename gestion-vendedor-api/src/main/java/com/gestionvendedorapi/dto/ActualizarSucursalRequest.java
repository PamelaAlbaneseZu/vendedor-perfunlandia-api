package com.gestionvendedorapi.dto;

import lombok.Data;

// Estos son los datos que se reciben para actualizar un vendedor
@Data
public class ActualizarSucursalRequest
{
    private Integer idVendedor;
    private Integer nuevaSucursal;

}