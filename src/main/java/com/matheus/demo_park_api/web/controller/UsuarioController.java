package com.matheus.demo_park_api.web.controller;

import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.service.UsuarioService;
import com.matheus.demo_park_api.web.dto.UsuarioCreateDto;
import com.matheus.demo_park_api.web.dto.UsuarioResponseDto;
import com.matheus.demo_park_api.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto usuarioCreateDto){
      Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
      return  ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        Usuario user = usuarioService.BuscarPorId(id);
        return  ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario user = usuarioService.aditarSenha(id, usuario.getPassword());
        return  ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List <Usuario>> getAll(){
         List<Usuario> users = usuarioService.BuscarTodos();
        return  ResponseEntity.ok(users);
    }
}
