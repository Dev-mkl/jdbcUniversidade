package org.example.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTable {

    public void createTableAluno(){
        String sql = """
                    CREATE TABLE IF NOT EXISTS aluno(
                    id_aluno int NOT NULL ,
                    matricula int NOT NULL,
                    nome varchar(255) NOT NULL,
                    telefone varchar(25),
                    data_de_nascimento date NOT NULL,
                    cpf varchar(15) NOT NULL,
                    id_curso integer NOT NULL,
                    CONSTRAINT aluno_pkeyteste PRIMARY KEY (id_aluno),
                    CONSTRAINT aluno_matricula_keyteste UNIQUE (matricula),
                    CONSTRAINT matricula_uniqueteste UNIQUE (matricula),
                    CONSTRAINT fk_id_cursoteste FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
                    )
                """;

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Não foi possível criar a tabela aluno");
            e.printStackTrace();
        }
    }

    public void createTableCurso(){
        String sql = """
                CREATE TABLE IF NOT EXISTS curso
                (
                    nome varchar(100) NOT NULL,
                    id_curso serial NOT NULL,
                    CONSTRAINT curso_pkey PRIMARY KEY (id_curso),
                    CONSTRAINT unique_nome UNIQUE (nome)
                )
                """;

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Não foi possível criar a tabela curso");
            e.printStackTrace();
        }
    }

    public void createTableAdmin(){
        String sql = """
                CREATE TABLE IF NOT EXISTS admin(
                id serial primary key NOT NULL,
                nome varchar(255) NOT NULL,
                senha char(12) not null,
                login varchar(100) NOT NULL
                )
                """;

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Não foi possível criar a tabela admin");
            e.printStackTrace();
        }
    }
}
