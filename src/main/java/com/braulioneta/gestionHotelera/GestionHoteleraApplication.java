package com.braulioneta.gestionHotelera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal de la aplicación Spring Boot que inicia el sistema de gestión hotelera
@SpringBootApplication // Indica que esta clase es un componente de configuración de Spring Boot
public class GestionHoteleraApplication {
    
    /**
     * Método principal que se utiliza como punto de entrada para la aplicación.
     * 
     * Este método se encarga de iniciar el contexto de la aplicación Spring Boot,
     * configurando automáticamente los componentes necesarios y lanzando el servidor embebido.
     * 
     * @param args Argumentos de línea de comandos que pueden ser pasados al iniciar la aplicación.
     */
    public static void main(String[] args) {
        // Inicia la aplicación Spring Boot y lanza el servidor embebido
        SpringApplication.run(GestionHoteleraApplication.class, args);
    }
}