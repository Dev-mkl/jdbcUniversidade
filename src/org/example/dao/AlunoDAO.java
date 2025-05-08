package org.example.dao;

import org.example.model.Aluno;
import org.example.factory.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class AlunoDAO {

    public void recuperarMatricula(String cpf ){
        String sql = "Select * from aluno where cpf = ?";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                System.out.println("Nome:");
                System.out.println(rs.getString("nome"));
                System.out.println("Matricula:");
                System.out.println(rs.getLong("matricula"));
            }
        }catch (SQLException e){
            System.out.println(("Não foi possível recuperar matricula"));
            e.printStackTrace();
        }
    }

    public Aluno getAluno(long matricula) {
        String sql = "select * from aluno where matricula = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setLong(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Aluno aluno = new Aluno(rs.getString("nome"), rs.getLong("telefone"), rs.getString("data_de_nascimento"), rs.getString("cpf"), rs.getInt("id_curso"), rs.getString("senha"));
                aluno.setMatricula(rs.getLong("matricula"));
                aluno.setDataNascimento(rs.getDate("data_de_nascimento").toLocalDate());
                return aluno;
            }
        }catch (SQLException e){
            System.out.println("Erro ao obter aluno" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public void insert(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, telefone, data_de_nascimento,cpf,id_curso) VALUES (?,?,?,?,?,?) RETURNING id_aluno";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setLong(3, aluno.getTelefone());
            stmt.setDate(4, java.sql.Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getCpf());
            stmt.setInt(6, aluno.getId_curso());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                aluno.setId(rs.getInt("id_aluno"));
                System.out.println("Aluno inserido com sucesso!");
                System.out.printf("Nome: %s\n", aluno.getNome());
                System.out.printf("Matricula: %s\n", aluno.getMatricula());

            }
        } catch (SQLException e){
            System.out.println("Erro ao inserir aluno");
            e.printStackTrace();
        }
    }

    public void select(Long matricula) {
        String sql = "SELECT aluno.nome as aluno_nome, aluno.matricula as aluno_matricula, curso.nome as curso_nome FROM aluno join curso on aluno.id_curso = curso.id_curso where matricula = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setLong(1, matricula);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                System.out.println("====Dados do aluno====");
                System.out.printf("Nome: %s\n", rs.getString("Aluno_nome"));
                System.out.printf("Matricula: %d\n", rs.getLong("Aluno_matricula"));
                System.out.printf("Curso: %s\n", rs.getString("curso_nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro exibir aluno");
            e.printStackTrace();
        }
    }

    public void delete(long matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setLong(1, matricula);

            int rowsAcffectd= stmt.executeUpdate();
            if(rowsAcffectd > 0){
                System.out.println("Aluno removido com sucesso");
            }else{
                throw new SQLException("Erro ao remover aluno");
            }
        } catch (SQLException e) {
            System.out.println(e + e.getMessage());
        }
    }

    public void update(Aluno aluno, long matriculaAntiga) {
        String sql = "update aluno set nome = ?, matricula = ?,telefone = ?,data_de_nascimento = ?,cpf = ?,id_curso = ? where matricula = ? returning id_aluno";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, aluno.getNome());
            stmt.setLong(2, aluno.getMatricula());
            stmt.setLong(3, aluno.getTelefone());
            stmt.setDate(4, java.sql.Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getCpf());
            stmt.setInt(6, aluno.getId_curso());
            stmt.setLong(7, matriculaAntiga);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("alterado com sucesso");
                System.out.printf("Nome: %s\n", aluno.getNome());
                System.out.printf("Matricula: %d\n", aluno.getMatricula());
            }
        }catch (SQLException e){
            System.out.println("Erro ao atualizar aluno");
            e.printStackTrace();
        }
    }
}
