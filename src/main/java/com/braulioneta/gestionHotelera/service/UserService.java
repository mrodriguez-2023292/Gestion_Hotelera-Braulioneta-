package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.UserRepository;
import com.braulioneta.gestionHotelera.service.IService.IUserService;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;

    //@Autowired
   // BCryptSecurity bCryptSecurity;

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
        //Logica pendiente

        return userRepository.save(user);
    }

    @Override
    public boolean login(String username, String password) {
        // Logica pendiente
        return true;
    }


}
