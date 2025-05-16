import org.example.dao.AlunoDAO;
import org.example.dao.AutenticadorDAO;
import org.example.dao.CursoDAO;
import org.example.factory.CreateTable;
import org.example.model.*;
import org.example.dao.AdminDAO;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        AutenticadorDAO autenticadorDAO = new AutenticadorDAO();
        Scanner sc = new Scanner(System.in);
        /*CreateTable ct = new CreateTable();
        ct.createTableAluno();
        ct.createTableCurso();
        ct.createTableAdmin();*/
        while (true) {
            AlunoDAO alunoDAO = new AlunoDAO();
            try {
                System.out.println("=== Tela de Login ===");

                System.out.print("Usuário:\n1.Esqueci meu usuário\nPara sair digite 0");
                String usuario = sc.nextLine();
                if(usuario.equals("0")){
                    System.out.println("Até logo!!!");
                    break;
                }
                if(usuario.equals("1")){
                    System.out.println("Digite seu cpf: ");
                    String cpf = sc.nextLine();
                    alunoDAO.recuperarMatricula(cpf);
                    continue;
                }
                System.out.print("Senha: ");
                String senha = sc.nextLine();
                if (usuario.matches("^\\d+$") && autenticadorDAO.autenticarUsuario(Long.parseLong(usuario), senha)) {
                    AlunoDAO alunoDao = new AlunoDAO();
                    System.out.println("Login efetuado com sucesso!");
                    System.out.println("Bem Vindo ao Sistema!");
                    MenuUsuario menuUsuario = new MenuUsuario(alunoDAO.getAluno(Long.parseLong(usuario)));
                    menuUsuario.acessoMenuUsuario();
                }
                if (!usuario.matches("^\\d+$") && autenticadorDAO.autenticarAdmin(usuario, senha)) {
                    AdminDAO adminDao = new AdminDAO();
                    System.out.println("Login efetuado com sucesso!");
                    System.out.println("Bem Vindo ao Sistema!");
                    MenuAdmin menuAdmin = new MenuAdmin(adminDao.select(usuario));
                } else {
                    System.out.println("Usuário ou senha incorreto.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}