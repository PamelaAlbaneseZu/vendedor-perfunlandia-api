package com.gestionvendedorapi.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

// DTO para mostrar la informacion del vendedor
@Data
public class VendedorDTO extends RepresentationModel<VendedorDTO>
{

    private Integer idVendedor;
    private Integer idUsuario;     // Api GestionUsuario
    private String nombreUsuario;  // Api GestionUsuario
    private Integer idSucursal;   // Api GestionLocalizacion
    
}
