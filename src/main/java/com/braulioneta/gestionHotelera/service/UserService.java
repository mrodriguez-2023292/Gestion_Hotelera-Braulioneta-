package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.UserRepository;
import com.braulioneta.gestionHotelera.service.IService.IUserService;
import com.braulioneta.gestionHotelera.utils.BCryptSecurity;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

     @Autowired
    BCryptSecurity bCryptSecurity;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User register(User user) {
        if(user.getPassword() != null){
            user.setPassword(bCryptSecurity.encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        if(user == null) {
            System.out.println("Usuario no encontrado.");
            return false; // Usuario no existe
        }
        
        if (!bCryptSecurity.checkPassword(password, user.getPassword())) {
            System.out.println("Contraseña incorrecta para el usuario: " + username);
            return false;
        }
        
        
        System.out.println("Usuario autenticado correctamente.");
        return true; // Inicio de sesión exitoso
    }
    

}
