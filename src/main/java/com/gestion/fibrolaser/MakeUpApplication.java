package com.gestion.fibrolaser;

import com.gestion.fibrolaser.entidades.DataInicializador;
import com.gestion.fibrolaser.repositorios.RolRepository;
import com.gestion.fibrolaser.repositorios.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MakeUpApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MakeUpApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder aplication) {
		return  aplication.sources(MakeUpApplication.class);
	}
}
