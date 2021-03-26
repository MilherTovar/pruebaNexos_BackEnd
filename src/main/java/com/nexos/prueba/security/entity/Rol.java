package com.nexos.prueba.security.entity;

import javax.persistence.*;

import com.nexos.prueba.security.enums.RolNombre;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name="rol_nombre", nullable = false)
    private RolNombre rolNombre;
    public Rol() {
    }
    public Rol(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public RolNombre getRolNombre() {
        return rolNombre;
    }
    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
    
}
