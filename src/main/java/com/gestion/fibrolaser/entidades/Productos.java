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
public class Productos {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_prod")
    private Integer id;
    @Column(name = "nombre_prod")
    private String nombre;
    @OneToOne
    @JoinColumn(name = "fk_categoria")
    private Categorias categoria;
    private BigDecimal precio;
    private BigDecimal stock;
    @Column(name = "producto_img")
    private String producto_img;

}
