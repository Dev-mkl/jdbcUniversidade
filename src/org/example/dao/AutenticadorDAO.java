package org.example.dao;

import org.example.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticadorDAO {

    public boolean autenticarUsuario(String usuario, String senha) {
        String sql = "select * from aluno where usuario = ? return senha";
        String senhaBusca = "";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                System.out.println("Usuário não encontrado!");
               return false;
            }
            senhaBusca = rs.getString("senha");
        }catch (SQLException e) {
            System.out.println("Erro ao autenticar usuario!");
            e.printStackTrace();
        }
        if(senha.equals(senhaBusca)){
            return true;
        }
        return false;
    }

    public boolean autenticarAdmin(String usuario, String senha) {
        String sql = "select * from admin where usuario = ? return senha";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();
            if(!rs.next()){
                System.out.println("Admin não encontrado!");
                return false;
            }
            String senhaBusca = rs.getString("senha");
            if(senha.equals(senhaBusca)){
                return true;
            }
        }catch (SQLException e) {
            System.out.println("Erro ao autenticar administrador!");
            e.printStackTrace();
        }
        return false;
    }
}
