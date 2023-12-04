package com.gestion.fibrolaser.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PedidoDetalle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "fk_pedido")
    @ManyToOne
    private Pedido pedidoId;
    @ManyToOne
    @JoinColumn(name = "producto_id_prod")
    private Productos producto;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;

}
