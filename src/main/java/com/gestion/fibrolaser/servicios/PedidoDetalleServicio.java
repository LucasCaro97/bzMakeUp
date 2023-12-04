package com.gestion.fibrolaser.servicios;

import com.gestion.fibrolaser.entidades.PedidoDetalle;
import com.gestion.fibrolaser.repositorios.PedidoDetalleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoDetalleServicio {

    private final PedidoDetalleRepository pedidoDetalleRepository;

    @Transactional
    public void crear(PedidoDetalle pedidoDetalle){
        pedidoDetalleRepository.save(pedidoDetalle);
    }

    @Transactional(readOnly = true)
    public List<PedidoDetalle> getById(Integer id) {
        return pedidoDetalleRepository.obtenerPorIdPedido(id);
    }
}
