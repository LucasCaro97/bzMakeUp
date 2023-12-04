package com.gestion.fibrolaser.servicios;

import com.gestion.fibrolaser.DTO.PedidoDTO;
import com.gestion.fibrolaser.entidades.Pedido;
import com.gestion.fibrolaser.entidades.PedidoDetalle;
import com.gestion.fibrolaser.entidades.Usuario;
import com.gestion.fibrolaser.repositorios.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServicio {

    private final PedidoDetalleServicio pedidoDetalleServicio;

    private final PedidoRepository pedidoRepository;
    private final ProductoServicio productoServicio;

    @Transactional
    public void create(Pedido pedidoDto, List<PedidoDTO> listaProductos){
        Pedido pedido = new Pedido();
        pedido.setCliente(pedidoDto.getCliente());
        pedido.setDescripcion(pedidoDto.getDescripcion());
        pedido.setFechaIngreso(LocalDate.now());
        pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedido.setSenia(pedidoDto.getSenia());
        pedido.setUsuario(pedidoDto.getUsuario());
        pedido.setNroTelefono(pedidoDto.getNroTelefono());
        pedido.setCorreoElectronico(pedidoDto.getCorreoElectronico());
        pedidoRepository.save(pedido);

        Pedido pedidoAlmacenado =  pedidoRepository.findFirstByOrderByIdDesc();

        for (PedidoDTO p : listaProductos) {
            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setPedidoId(pedidoAlmacenado);
            pedidoDetalle.setProducto(productoServicio.obtenerPorNombre(p.getNombre()));
            pedidoDetalle.setCantidad(p.getCantidad());
            pedidoDetalle.setPrecio(p.getPrecio());
            pedidoDetalle.setSubtotal(p.getPrecio().multiply(new BigDecimal(p.getCantidad())));
            pedidoDetalleServicio.crear(pedidoDetalle);
        }

    }

    @Transactional
    public void update(Pedido pedidoDto){

        Pedido pedido = pedidoRepository.findById(pedidoDto.getId()).get();
        pedido.setCliente(pedidoDto.getCliente());
        pedido.setDescripcion(pedidoDto.getDescripcion());
        pedido.setFechaIngreso(LocalDate.now());
        pedido.setFechaEntrega(pedidoDto.getFechaEntrega());
        pedido.setEstadoPedido(pedidoDto.getEstadoPedido());
        pedido.setSenia(pedidoDto.getSenia());
        pedido.setUsuario(pedidoDto.getUsuario());
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> getAll(){
        Sort sort = Sort.by("fechaEntrega").ascending();
        return pedidoRepository.findAll(sort);

    }

    @Transactional(readOnly = true)
    public List<Pedido> getByUser(Integer id){
        return pedidoRepository.searchByUsuarioNativeQUery(id);

    }

    @Transactional(readOnly = true)
    public List<Pedido> getByUserObject(Usuario usuario){
        return pedidoRepository.findByUsuario(usuario);

    }


    @Transactional
    public Pedido getById(Integer id){ return pedidoRepository.findById(id).get(); }

    @Transactional
    public void deleteById(Integer id) { pedidoRepository.deleteById(id);}

}

