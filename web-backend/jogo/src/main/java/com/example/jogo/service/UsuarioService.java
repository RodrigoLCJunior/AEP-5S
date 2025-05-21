/*
 ** Task..: 13 - Sistema Inicial do Combate
 ** Data..: 09/03/2025
 ** Autor.: Rodrigo Luiz
 ** Motivo: Criar classe Usuario
 ** Obs...:
 */

package com.example.jogo.service;
import com.example.jogo.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.jogo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse login(Usuarios loginRequest) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, "email", "Email não encontrado", null);
        }

        Usuarios usuario = usuarioOpt.get();

        if (passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            return new LoginResponse(true, null, null, usuario);
        } else {
            return new LoginResponse(false, "senha", "Senha incorreta", null);
        }
    }

    public Optional<Usuarios> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuarios buscarUsuarioPorId(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional // Simples, sem rollbackOn
    public Usuarios criarUsuario(Usuarios usuarioRequest) throws IllegalArgumentException {
        // Validação de campos obrigatórios
        if (usuarioRequest.getNome() == null || usuarioRequest.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (usuarioRequest.getEmail() == null || usuarioRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }

        if (usuarioRequest.getSenha() == null || usuarioRequest.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        if (usuarioRequest.getCep() == null || usuarioRequest.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("O CEP é obrigatório.");
        }


        // Verifica se o email já existe
        Optional<Usuarios> existingUser = usuarioRepository.findByEmail(usuarioRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(usuarioRequest.getSenha());
        Usuarios usuario = new Usuarios();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setCep(usuarioRequest.getCep());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuarios modificarUsuario(UUID id, Usuarios usuarios) {
        Usuarios usuarioVelho = usuarioRepository.findById(id).orElse(null);
        if (usuarioVelho.getNome() == null || usuarioVelho.getNome().trim().isEmpty() ||
                usuarioVelho.getEmail() == null || usuarioVelho.getEmail().trim().isEmpty() ||
                usuarioVelho.getSenha() == null || usuarioVelho.getSenha().trim().isEmpty() ||
                usuarioVelho.getCep() == null || usuarioVelho.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("Campos obrigatórios estão vazios");
        }

        usuarioVelho.setNome(usuarios.getNome());
        usuarioVelho.setEmail(usuarios.getEmail());
        usuarioVelho.setCep(usuarios.getCep());
        usuarioVelho.setSenha(passwordEncoder.encode(usuarios.getSenha()));
        return usuarioRepository.save(usuarioVelho);
    }

    @Transactional
    public Usuarios salvarUsuario(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
}