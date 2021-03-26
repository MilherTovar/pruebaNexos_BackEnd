package com.nexos.prueba.security.controller;

import com.nexos.prueba.security.service.UsuarioService;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.nexos.prueba.security.dto.JwtDto;
import com.nexos.prueba.security.dto.LoginUsuario;
import com.nexos.prueba.security.dto.NuevoUsuario;
import com.nexos.prueba.security.entity.Rol;
import com.nexos.prueba.security.entity.Usuario;
import com.nexos.prueba.security.enums.RolNombre;
import com.nexos.prueba.security.jwt.JwtProvider;
import com.nexos.prueba.security.service.RolService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.Mensaje;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;

    public final static Logger logger=LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"),HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("Usuario ya existe"),HttpStatus.BAD_REQUEST);
        Usuario usuario=
            new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getApellido(),nuevoUsuario.getEmail(),
            passwordEncoder.encode(nuevoUsuario.getPassword()),true,nuevoUsuario.getFechaNacimiento());
        Set<Rol>roles=new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.Asesor).get());
        logger.info(nuevoUsuario.getRoles().toString());
        if(nuevoUsuario.getRoles().contains("Administrador"))
            roles.add(rolService.getByRolNombre(RolNombre.Administrador).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("Operación exitosa"),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos o email invalido"),HttpStatus.BAD_REQUEST);
        Authentication authentication=
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtProvider.generateToken(authentication);
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            JwtDto jwtDto=new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());
            return new ResponseEntity(jwtDto,HttpStatus.OK);
    }
}
