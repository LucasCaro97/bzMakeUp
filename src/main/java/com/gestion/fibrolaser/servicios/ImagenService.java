package com.gestion.fibrolaser.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagenService {

    private static final String RUTA_ABSOLUTA = "/opt";

    public String copy(MultipartFile image){
        try{
            String photoName = image.getOriginalFilename();
            Path photoPath = Paths.get(RUTA_ABSOLUTA, photoName).toAbsolutePath();
            System.out.println(photoPath);
            Files.copy(image.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);
            return photoName;
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error saving image");
        }
    }



}
