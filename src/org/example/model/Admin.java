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
        String[] PrimeiroNome = nome.split(" ");
        String login = "admin/" + PrimeiroNome[0].toLowerCase();
        return this.login = login;
    }

    public Boolean verificaNome(){
        if (nome.matches("[^a-zA-ZÀ-ÖØ-öø-ÿ\\s]")){
            throw new IllegalArgumentException("Nome com formato incorreto. Verifique por favor e tente novamente.");
        }
        if (nome == null){
            throw new IllegalArgumentException("Nome não preenchido, por favor verifique e tente novamente.");
        }
        return true;
    }

    public boolean verificarSenha(){
        if (senha.length() < 8 || senha.length() > 12){
            throw new IllegalArgumentException("Senha inválida, a senha deve ter mais de 8  e menos de 12 caracteres.");
        }
        if(!senha.matches(".*[^a-zA-Z0-9].*")){
            throw new IllegalArgumentException("Senha inválida, a senha deve conter caracteres especiais.");
        }
        if (senha == null){
            throw new IllegalArgumentException("Senha inválida, a senha não pode ser nula.");
        }
        if(!senha.matches(".*[A-Z].*")){
            throw new IllegalArgumentException("Senha inválida, a senha deve conter letras maiúsculas.");
        }
        if(!senha.matches(".*[a-z].*")){
            throw new IllegalArgumentException("Senha inválida, a senha deve conter letras minúsculas.");
        }
        if(senha.contains(" ")){
            throw new IllegalArgumentException("Senha inválida, a senha não pode conter espaço.");
        }
        return true;
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
