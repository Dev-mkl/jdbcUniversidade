package org.example.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.zip.DataFormatException;

public class Aluno {
    private int id;
    private String nome;
    private long matricula;
    private long telefone;
    private LocalDate dataNascimento;
    private String dataString;
    private String cpf;
    private int id_curso;

    public Aluno(String nome, long telefone, String dataString, String cpf, int id_curso) {
        this.nome = nome;
        this.telefone = telefone;
        this.dataString= dataString;
        this.cpf = cpf;
        this.id_curso = id_curso;
    }

    public long gerarMatricula(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtual = date.format(formatter);

        String matricula = dataAtual.substring(6) + String.valueOf(id_curso) + cpf.substring(0, cpf.length() - 8);
        return this.matricula = Long.parseLong(matricula);
    }

    public String VerificaDataNascimento() {
        boolean DataNumero = dataString.matches("\\d+");
        if (!DataNumero) {
            throw new IllegalArgumentException("Erro: data de nascimento fora do padrão");
        }
        if (dataString.length() != 8) {
            throw new IllegalArgumentException("Erro: data de nascimento incorreta");
        }
        StringBuilder dataNascimentoBuilder = new StringBuilder(dataString);
        dataNascimentoBuilder.insert(2, "/");
        dataNascimentoBuilder.insert(5, "/");
        return dataString = dataNascimentoBuilder.toString();
    }
    public LocalDate DatedataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            dataNascimento = LocalDate.parse(dataString, formatter);
        }catch (DateTimeParseException e){
            System.out.println("Erro: data de nascimento incorreta" + e.getMessage());
        }
        return dataNascimento;
    }

//    Getters e Setters

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
    public long getTelefone() {
        return telefone;
    }
    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }
    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}
