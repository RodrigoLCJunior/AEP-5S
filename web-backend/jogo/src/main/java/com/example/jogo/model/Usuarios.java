/*
 ** Task.....: mob_001
 ** Motivo...: Classe principal do Usuario
 ** Usuario..: Rodrigo Luiz
 ** Data.....: 02/03/2025
 ** Obs......: Muito cuidado com a Autenticação
 */

package com.example.jogo.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;  // Armazenada de forma segura (hash)

    @Column(nullable = false)
    private String cep;

    public Usuarios() {
    }

    public Usuarios(String nome, String cep, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
