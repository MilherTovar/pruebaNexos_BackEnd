package com.nexos.prueba.security.repository;

import java.util.Optional;

import com.nexos.prueba.security.entity.Rol;
import com.nexos.prueba.security.enums.RolNombre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository <Rol,Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
