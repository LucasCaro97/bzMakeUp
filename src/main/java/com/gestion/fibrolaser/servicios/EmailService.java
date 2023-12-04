package com.gestion.fibrolaser.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendContactHomePage(String clienteNombre, String clienteCorreo, String mensaje) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bzmakeup.eldorado@gmail.com");
        message.setFrom(from);
        message.setSubject("Contacto Web - " + clienteNombre);
        //Obtengo el nombre, correo y mensaje en el controlador
        message.setText(generarMensaje(clienteNombre, clienteCorreo, mensaje));
        sender.send(message);
    }


    public String generarMensaje(String clienteNombre, String clienteCorreo, String mensaje) {
        String texto = "Nombre: " + clienteNombre + "\n" + "Correo: " + clienteCorreo + "\n" + mensaje;
        return texto;

    }

    public void notificarPedidoRealizado(String cliente, String correoElectronico) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("bzmakeup.eldorado@gmail.com");
        message.setFrom(from);
        message.setSubject("El cliente " + cliente + " ha realizado un pedido nuevo");
        //Obtengo el nombre, correo y mensaje en el controlador
        message.setText("Un nuevo pedido se ha realizado, contactar y notificar al cliente que se ha recibido el pedido y pasar informacion sobre el pago y el envio" + "\n" + " Correo: " + correoElectronico);
        sender.send(message);


    }
}
