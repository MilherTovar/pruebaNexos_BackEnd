package com.nexos.prueba.security.repository;

import java.util.Optional;

import com.nexos.prueba.security.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario>findByEmail(String email);
    boolean existsByEmail(String email);
}
