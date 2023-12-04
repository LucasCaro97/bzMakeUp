package com.gestion.fibrolaser.controladores;


import com.gestion.fibrolaser.entidades.Categorias;
import com.gestion.fibrolaser.entidades.Productos;
import com.gestion.fibrolaser.servicios.CategoriaServicio;
import com.gestion.fibrolaser.servicios.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoControlador {

    private final ProductoServicio productoServicio;
    private final CategoriaServicio categoriaServicio;


    @GetMapping
    public ModelAndView getAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("tabla-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));
        mav.addObject("productos", productoServicio.getAll());
        mav.addObject("categorias", categoriaServicio.getAll());
        return mav;
    }


    @GetMapping("/form")
    public ModelAndView getForm(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("form-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(inputFlashMap != null) {
            mav.addObject("exception", inputFlashMap.get("exception"));
            mav.addObject("producto", inputFlashMap.get("producto"));
        }else{
            mav.addObject("producto", new Productos());
        }
        mav.addObject("listaCategorias", categoriaServicio.getAll());
        mav.addObject("action", "create");
        return mav;
    }

    @GetMapping("/form/{id}")
    public ModelAndView getFormUpd(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("form-productos");
        mav.addObject("producto", productoServicio.getById(id));
        mav.addObject("listaCategorias", categoriaServicio.getAll());
        mav.addObject("action", "update");
        return mav;
    }

    @PostMapping("/create")
    public RedirectView create(Productos dto, @RequestParam("photo") MultipartFile file, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/productos");
        try{
            productoServicio.createProducto(dto, file);
            attributes.addFlashAttribute("exito", "La operacion se ha realizado con exito");
        }catch(Exception e){
            attributes.addFlashAttribute("producto", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/productos/form");
        }

        return redirect;
    }

    @PostMapping("/update")
    public RedirectView update(Productos dto,@RequestParam("photo") MultipartFile file, RedirectAttributes attributes){
        RedirectView redirect = new RedirectView("/productos");
        try{
            productoServicio.actualizar(dto, file);
            attributes.addFlashAttribute("exito", "La operacion se ha realizado con exito");
        }catch(Exception e){
            attributes.addFlashAttribute(   "producto", dto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/productos/form");
        }

        return redirect;
    }


}
