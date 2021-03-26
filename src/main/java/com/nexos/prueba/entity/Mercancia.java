package com.nexos.prueba.entity;

import java.util.Date;

import javax.persistence.*;

import com.nexos.prueba.security.entity.Usuario;

@Entity
public class Mercancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre_producto",nullable = false, unique = true)
    private String nombreProducto;
    @Column(name="cantidad_producto",nullable = false)
    private int cantidad;
    @Column(name="fecha_ingreso",nullable = false)
    private Date fechaIngreso;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Id_usuariocreo")
    private Usuario usuarioIngreso;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Id_usuariomodifico")
    private Usuario usuarioModificacion;
    @Column(name="fecha_modificacion",nullable = true)
    private Date fechaModificacion;  
}
