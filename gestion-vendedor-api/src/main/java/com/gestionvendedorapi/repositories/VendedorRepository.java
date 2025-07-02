package com.gestionvendedorapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionvendedorapi.models.VendedorDetalle;
import java.util.List;

// Esta es la interfaz que permite consultar la tabla vendedor_detalle
public interface VendedorRepository extends JpaRepository<VendedorDetalle, Integer> {

    List<VendedorDetalle> findByIdSucursal(Integer idSucursal);

}
