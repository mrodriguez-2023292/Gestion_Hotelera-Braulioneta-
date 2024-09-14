package com.braulioneta.gestionHotelera.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

//Configuración para BCrypt | Librería para encriptación de datos.
@Component
public class BCryptSecurity {

    //Ocultar la password hasta 12 capas de encriptacion
    public String encodePassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    //Comparacion de la contraseña normal y la contraseña hashed (password encriptada)
    public boolean checkPassword(String password, String hashedPassword){
        BCrypt.Result res = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return res.verified;
    }

}