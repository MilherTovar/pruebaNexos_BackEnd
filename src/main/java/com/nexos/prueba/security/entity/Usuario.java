package com.nexos.prueba.security.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre",nullable = false)
    private String nombre;
    @Column(name="apellido",nullable = false)
    private String apellido;
    @Column(name="email",nullable=false,unique=true)
    private String email;
    @Column(name="password",nullable = false)
    private String password;
    @ManyToMany
    @JoinTable(name="usuario_rol",joinColumns = @JoinColumn(name="usuario_id"),
    inverseJoinColumns = @JoinColumn(name="rol_id"))
    private Set<Rol> roles=new HashSet<>();
    @Column(name="estado")
    private boolean estado;
    @Column(name="fecha_nacimiento",nullable = false)
    private Date fechaNacimiento;
    public Usuario() {
    }
    public Usuario(String nombre, String apellido, String email, String password, boolean estado,
    Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.estado=estado;
        this.fechaNacimiento=fechaNacimiento;
    }
    public boolean getEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado=estado;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Rol> getRoles() {
        return roles;
    }
    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        String retorno="Nombre: "+this.nombre+
                        " Apellido: "+this.apellido+
                        " email: "+this.email+
                        " roles: " + this.roles.toString();
        return retorno;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
       
}
