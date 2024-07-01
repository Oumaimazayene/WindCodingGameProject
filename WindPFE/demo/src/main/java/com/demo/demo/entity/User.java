package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Data
public class User {
        @Id
        private Long id;
        private String firstname;
        private String lastname;
        private  String email;
        private String password ;
        private boolean isDeleted ;
        private boolean isVerified;
        private String societyName;
        private String numtel;
        private String Logo;
        private String Matricule_fiscale;
        private UUID uuid;

    }
