package com.matheus.demo_park_api.service;


import com.matheus.demo_park_api.entity.Usuario;
import com.matheus.demo_park_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario BuscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );
    }

    @Transactional
    public Usuario aditarSenha(Long id, String password) {
        Usuario user = BuscarPorId(id);
        user.setPassword(password);
        return user;
    }
}
