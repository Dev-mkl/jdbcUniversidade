package org.example.dao;

import org.example.model.Aluno;
import org.example.factory.ConnectionFactory;

import java.sql.*;


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
                aluno.setId(rs.getInt("id_aluno"));
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
        String sql = "SELECT *, curso.nome as curso_nome FROM aluno join curso on aluno.id_curso = curso.id_curso where matricula = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setLong(1, matricula);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                System.out.println("====Dados do aluno====");
                System.out.printf("Nome: %s\n", rs.getString("nome"));
                System.out.printf("Matricula: %d\n", rs.getLong("matricula"));
                System.out.printf("Curso: %s\n", rs.getString("curso_nome"));
                System.out.println("Telefone: " + rs.getLong("telefone"));
                System.out.println("Data de nascimento: " + rs.getDate("data_nascimento"));
                System.out.println("CPF: " + rs.getString("cpf"));
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
        String sql = "update aluno set nome = ?,telefone = ?,data_de_nascimento = ?,cpf = ?,id_curso = ?, senha = ? where matricula = ? returning id_aluno";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, aluno.getNome());
            stmt.setLong(2, aluno.getTelefone());
            stmt.setDate(3, java.sql.Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(4, aluno.getCpf());
            stmt.setInt(5, aluno.getId_curso());
            stmt.setString(6, aluno.getSenha());
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

    public void listarAlunos(){
        String sql = "select * from aluno";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String nome = rs.getString("nome");
                int id = rs.getInt("id_aluno");
                long matricula = rs.getLong("matricula");

                System.out.printf("%s\t\t\t\t\t%d\t\t%d", nome, id, matricula);
            }
        }catch (SQLException e){
            System.out.println("Erro ao listar alunos");
            e.printStackTrace();
        }
    }

    public int getMaxId(){
        String sql = "select max(id_aluno) from aluno";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(rs.getInt(0) + 1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar numero de id");
            e.printStackTrace();
        }
        return 1;
    }
}
