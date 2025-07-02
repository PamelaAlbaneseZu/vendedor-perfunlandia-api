package com.gestionvendedorapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "vendedor_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendedorDetalle extends RepresentationModel<VendedorDetalle>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;   //FK desde Api Gestion de Usuarios

    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;   // FK desde Api Gestion Localizacion

}


