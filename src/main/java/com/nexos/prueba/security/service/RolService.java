package com.nexos.prueba.security.service;

import java.util.Optional;

import com.nexos.prueba.security.entity.Rol;
import com.nexos.prueba.security.enums.RolNombre;
import com.nexos.prueba.security.repository.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {
    
    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }
}
