package com.gestion.fibrolaser.repositorios;

import com.gestion.fibrolaser.entidades.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {


    @Query(value = "SELECT * FROM pedido_detalle WHERE fk_pedido = ?" , nativeQuery = true)
    List<PedidoDetalle> obtenerPorIdPedido(Integer id);

}
