package com.gestion.fibrolaser.controladores;


import com.gestion.fibrolaser.DTO.PedidoDTO;
import com.gestion.fibrolaser.entidades.Pedido;
import com.gestion.fibrolaser.servicios.EmailService;
import com.gestion.fibrolaser.servicios.EstadoPedidoServicio;
import com.gestion.fibrolaser.servicios.PedidoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.channels.ScatteringByteChannel;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/carrito")
@RequiredArgsConstructor
public class CarritoControlador {

    private final EstadoPedidoServicio estadoPedidoServicio;
    private final PedidoServicio pedidoServicio;
    private final EmailService emailService;


    @GetMapping
    public ModelAndView getCarrito(){
        ModelAndView mav = new ModelAndView("tabla-carrito");
        return mav;
    }


        @PostMapping("/enviarPedido/{cliente}/{correoElectronico}")
        public ResponseEntity<String> enviarPedido(@RequestBody List<PedidoDTO> listaProductos, @PathVariable String cliente, @PathVariable String correoElectronico){
            String mensaje = "";
        try {

                if(cliente == null || cliente.equals("") || correoElectronico == null){
                    throw new Exception("Telefono y Nombre son requeridos");
                }else {
                    for (PedidoDTO p : listaProductos) {
                        System.out.println(p);
                    }

                    Pedido pedido = new Pedido();
                    pedido.setEstadoPedido(estadoPedidoServicio.getById(1));
                    pedido.setDescripcion(cliente.replace("-", " "));
                    pedido.setCorreoElectronico(correoElectronico.toString());
                    System.out.println(correoElectronico);
                    pedido.setFechaIngreso(LocalDate.now());
                    pedidoServicio.create(pedido, listaProductos);
                    emailService.notificarPedidoRealizado(cliente, correoElectronico);
                    mensaje = "Pedido registrado correctamente";
                    //crear metodo para enviar un correo a bzmake up notificacndo que se genero un nuevo pedido

                }

        }catch (Exception e){
            e.printStackTrace();
            mensaje = "Error al registrar el pedido";
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }




}
