package com.gestion.fibrolaser.servicios;

import com.gestion.fibrolaser.entidades.Productos;
import com.gestion.fibrolaser.repositorios.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicio {

    private final ProductoRepository productoRepository;
    private final ImagenService imagenService;

    @Transactional
    public void createProducto(Productos dto, MultipartFile file){
        Productos productos = new Productos();
        productos.setNombre(dto.getNombre());
        productos.setCategoria(dto.getCategoria());
        productos.setPrecio(dto.getPrecio());
        productos.setStock(dto.getStock());
        if(!file.isEmpty()){
            productos.setProducto_img(file.getOriginalFilename());
            imagenService.copy(file);
        }
        productoRepository.save(productos);
    }

    @Transactional
    public void actualizar(Productos dto, MultipartFile file){
        Productos productos = productoRepository.findById(dto.getId()).get();
        productos.setNombre(dto.getNombre());
        productos.setCategoria(dto.getCategoria());
        productos.setPrecio(dto.getPrecio());
        productos.setStock(dto.getStock());
        if(!file.isEmpty()){
            productos.setProducto_img(file.getOriginalFilename());
            imagenService.copy(file);
        }
        productoRepository.save(productos);
    }

    @Transactional(readOnly = true)
    public List<Productos> getAll(){ return productoRepository.findAll(); }

    @Transactional(readOnly = true)
    public Productos getById(Integer id){ return productoRepository.findById(id).get(); }

    @Transactional
    public void deleteById(Integer id){ productoRepository.deleteById(id);}

    @Transactional(readOnly = true)
    public Productos obtenerPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }
}
