package com.braulioneta.gestionHotelera.model;

import com.braulioneta.gestionHotelera.utils.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Column(unique = true)
    private String username;


    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;
    
    @NotBlank
    private String phone; 

    @Enumerated(EnumType.STRING)
    @NotNull
    private Roles userType;
}
