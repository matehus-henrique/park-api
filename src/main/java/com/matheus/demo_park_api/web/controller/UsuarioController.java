package com.matheus.demo_park_api.web.controller;

import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.service.UsuarioService;
import com.matheus.demo_park_api.web.dto.UsuarioCreateDto;
import com.matheus.demo_park_api.web.dto.UsuarioResponseDto;
import com.matheus.demo_park_api.web.dto.UsuarioSenhaDto;
import com.matheus.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.matheus.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios" , description = "Contém todas operações")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @Operation(summary = "Criar um novo usuário", description = "Recursu para criar um novo usuário",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso :)",
                        content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDto.class ))),
                @ApiResponse(responseCode = "409", description = "Usuário e e-mail cadastro no sistema",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não precessado por dados de entrada invalidos ",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto){
      Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
      return  ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.BuscarPorId(id);
        return  ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.aditarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(),dto.getConfirmaSenha());
        return  ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List <UsuarioResponseDto>> getAll(){
         List<Usuario> users = usuarioService.BuscarTodos();
        return  ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
