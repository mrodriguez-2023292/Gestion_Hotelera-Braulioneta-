package com.braulioneta.gestionHotelera.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

//Configuración para BCrypt | Librería para encriptación de datos.
@Component
public class BCryptSecurity {

    // Método para encriptar la contraseña con un costo de 12
    public String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Método para verificar si la contraseña en texto plano coincide con el hash
    public boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

}