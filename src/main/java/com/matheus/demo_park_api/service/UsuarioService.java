package com.matheus.demo_park_api.service;


import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.exception.EntityNotFoundException;
import com.matheus.demo_park_api.exception.UsernameUniqueViolationException;
import com.matheus.demo_park_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public Usuario BuscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public Usuario aditarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw  new RuntimeException("Nova senha não confere com a confirmação de senha ");
        }

        Usuario user = BuscarPorId(id);

        if (!user.getPassword().equals(senhaAtual)){
            throw  new RuntimeException("Sua senha não confere ");
        }

        user.setPassword(novaSenha);
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> BuscarTodos() {
        return usuarioRepository.findAll();
    }
}
