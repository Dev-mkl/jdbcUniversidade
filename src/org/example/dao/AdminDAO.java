package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.model.Admin;
import org.example.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public void insert(Admin admin) {
        String sql = """
                insert into admin(nome, senha, login) values (?, ?, ?) returning id
                """;
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getSenha());
            stmt.setString(3, admin.getLogin());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                System.out.println("Administrador cadastrado com sucesso!");
            }
        }catch (SQLException e){
            System.out.println("Erro ao inserir administrador!");
            e.printStackTrace();
        }
    }

    public void update(String nome, String senha, String login, int id) {
        String sql = "update admin set nome = ?, senha = ?, login = ? where id = ?";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setString(3, login);
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Administrador atualizado com sucesso!");
            }
        }catch (SQLException e){
            System.out.println("Erro ao atualizar administrador!");
            e.printStackTrace();
        }
    }

    public void delete(String login) {
        String sql = "delete from admin where login = ?";

        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, login);

            int rowsAffect = stmt.executeUpdate();
            if(rowsAffect > 0){
                System.out.println("Administrador deletado com sucesso!");
            }
        }catch (SQLException e){
            System.out.println("Erro ao deletar administrador!");
            e.printStackTrace();
        }
    }

    public Admin select(String login) {
        Admin admin = null;
        String sql = "select * from admin where login = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                throw new SQLException("Nenhum admin encontrado com o login!");
            }
            admin = new Admin(rs.getString("nome"), rs.getString("senha"));
            admin.setId(rs.getInt("id"));
            admin.setSenha(rs.getString("senha"));
            admin.setLogin(rs.getString("login"));
        } catch (SQLException e) {
            System.out.println("Erro ao selecionar administrador!");
            e.printStackTrace();
        }
        return admin;
    }
}
