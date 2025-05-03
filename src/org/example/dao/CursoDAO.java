package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoDAO {
    public Curso existeCurso(String nome) {
        String sql = "select * from curso where nome = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Curso curso = new Curso(rs.getString("nome"));
                curso.setId(rs.getInt("id_curso"));
                return curso;
            };
            } catch (SQLException e) {
            System.out.println("Curso nao encontrado." + e.getMessage());
            e.printStackTrace();
            }
        return null;
        }

//    CRUD
    public void insert(Curso curso) {

        String sql = "INSERT INTO curso(nome) VALUES (?) RETURNING id_curso";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                System.out.println("Curso foi adicionado com sucesso!!!");
                curso.setId(rs.getInt("id_curso"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir curso");
            e.printStackTrace();
        }
    }

    public void delete(String nomeCurso) {
        String sql = "DELETE FROM curso WHERE nome = ? RETURNING nome";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nomeCurso);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Curso deletado com sucesso: " + rs.getString("nome"));
            }else {
                System.out.println("Curso não cadastrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso");
            e.printStackTrace();
        }
    }

    public void select() {
        String sql = "SELECT * FROM curso";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            int i = 1;
            while (rs.next()) {
                    System.out.printf("%d %s\n",i,rs.getString("nome"));
                    i++;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao exibir curso");
            e.printStackTrace();
        }
    }

    public void update(Curso curso, String nome) {
        String sql = "UPDATE curso SET nome = ? WHERE nome = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nome);
            stmt.setString(2, curso.getNome());

            int rowsAfected= stmt.executeUpdate();
            if (rowsAfected > 0) {
                System.out.println("Curso atualizado com sucesso");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar curso");
            e.printStackTrace();
        }
    }
}
