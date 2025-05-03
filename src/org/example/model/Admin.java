package org.example.model;

public class Admin {
    private int id;
    private String nome;
    private String senha;
    private String login;

    public Admin(String nome, String senha){
        this.nome = nome;
        this.senha = senha;
    }

    public String criarLogin(){
        String[] loginCriar = nome.split(" ");
        return login;
    }

//    GETTERS E SETTERS3
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
