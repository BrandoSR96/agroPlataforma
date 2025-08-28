package com.bsaenz.agroPlataforma.model;

import com.bsaenz.agroPlataforma.enums.Rol;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "auth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nombre;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "auth_roles", joinColumns = @JoinColumn(name = "auth_id"))
    @Enumerated(EnumType.STRING)
    private List<Rol> roles;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}