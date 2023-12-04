package com.gestion.fibrolaser.controladores;


import com.gestion.fibrolaser.servicios.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {


    private final EmailService emailService;


    @PostMapping("/enviarCorreoHomeContact")
    public RedirectView enviarEmailContactoHome(@RequestParam String nombreContacto, @RequestParam String emailContacto, @RequestParam String mensajeContacto ){
        RedirectView redirect = new RedirectView("/email/exito");
        try{
            emailService.sendContactHomePage(nombreContacto, emailContacto, mensajeContacto);
            return redirect;
        }catch (Exception e){
            redirect.setUrl("/email/error");
            return redirect;
        }
    }

    @GetMapping("/exito")
    public ModelAndView mostrarMensajeExito(){
        ModelAndView mav = new ModelAndView("envioEmail-exito");
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView mostrarMensajeError(){
        ModelAndView mav = new ModelAndView("envioEmail-error");
        return mav;
    }




}
