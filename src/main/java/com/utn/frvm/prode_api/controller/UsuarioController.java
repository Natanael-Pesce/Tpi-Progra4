package com.utn.frvm.prode_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utn.frvm.prode_api.dtos.registrardto.UsuarioRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.UsuarioResponseDto;
import com.utn.frvm.prode_api.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDto> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    @PostMapping
    public UsuarioResponseDto crearUsuario(@Valid @RequestBody UsuarioRegistrarDto dto) {
        return usuarioService.crearUsuario(dto);
    }
    
}
