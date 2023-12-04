package com.gestion.fibrolaser.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PedidoDTO {
        private String nombre;
        private BigDecimal precio;
        private Integer cantidad;
        private String imagen;
}
