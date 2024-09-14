package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.User;

public interface IUserService {

    List<User> listUsers();

    User getUser(Long id);

    User register(User user);

    boolean login(String username, String password);
}
