package org.example.model;

import com.sun.tools.javac.Main;
import org.example.dao.AdminDAO;
import org.example.dao.AutenticadorDAO;

import java.util.Scanner;

public class MenuAdmin {
    private Admin admin;
    private AdminDAO adminDao;

    public MenuAdmin(Admin admin) {
        this.admin = admin;
        adminDao = new AdminDAO();
    }

    public void menuAdministrador() {
        try {
            while (true) {
                MenuCurso menuCurso = new MenuCurso(admin);
                MenuAluno menuAluno = new MenuAluno(admin);
                Scanner entry = new Scanner(System.in);

                System.out.println("======MENU======");
                System.out.println("1 - Curso");
                System.out.println("2 - Aluno");
                System.out.println("3 - Administrador");
                System.out.println("0 - Sair");
                System.out.println("Escolha uma opção:\n");
                int opcao = entry.nextInt();

                switch (opcao) {
                    case 1:
                        menuCurso.acessoMenuCurso();
                        break;
                    case 2:
                        menuAluno.acessoMenuAluno();
                        break;
                    case 3:
                        acessoMenuAdmin();
                    case 0:
                        String saida = "voltando para o menu";
                        System.out.println("=================================");
                        System.out.printf("   🔄 %s   \n", saida);
                        System.out.print("   Carregando");

                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.print(".");
                        }

                        System.out.println("\n=================================");
                        Main.main(null);
                    default:
                        System.out.println("Opção não disponivel.");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acessoMenuAdmin() {
        Scanner entrada = new Scanner(System.in);

        while (true) {
            try {
                Scanner entry = new Scanner(System.in);
                System.out.println("======MENU======");
                System.out.println("1 - Cadastrar administrador.");
                System.out.println("2 - Deletar administrador.");
                System.out.println("3 - Alterar dados do administrador.");
                System.out.println("4 - Exibir dados do administrador.");
                System.out.println("0 - Sair.");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarAdmin();
                        break;
                    case 2:
                        deletarAdmin();
                        break;
                    case 3:
                        alterarDadosAdmin();
                        break;
                    case 4:
                        exibirDadosAdmin();
                        break;
                    case 0:
                        String saida = "voltando para o menu";
                        System.out.println("=================================");
                        System.out.printf("   🔄 %s   \n", saida);
                        System.out.print("   Carregando");

                        for (int i = 0; i < 3; i++) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.print(".");
                        }

                        System.out.println("\n=================================");
                        Main.main(null);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void cadastrarAdmin() {
        Scanner entry = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("====Cadastrar administrador====");
                System.out.println("Digite seu nome:\ncaso queira sair digite 'sair'");
                String nome = entry.nextLine().toUpperCase();
                if (nome == "SAIR") {
                    break;
                }
                System.out.println("Digite uma  senha: ");
                System.out.println("deve conter:\n•Letra maúscula e minuscula;\n•Caracter especial;\n•Número;\n•Entre 8 a 12 Caracteres.");
                String senha = entry.nextLine();
                Admin admin = new Admin(nome, senha);
                if (admin.verificaNome() && admin.verificarSenha()) {
                    admin.criarLogin();
                    adminDao.insert(admin);
                    System.out.println("Deseja cadastrar outro administrador? (S/N)");
                    String resposta = entry.nextLine().toUpperCase();
                    switch (resposta) {
                        case "S":
                            cadastrarAdmin();
                            break;
                        case "N":
                            break;
                        default:
                            System.out.println("Opção inválida!");

                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void deletarAdmin() {
        AutenticadorDAO autenticadorDAO = new AutenticadorDAO();
        Scanner entry = new Scanner(System.in);
        while (true) {
            System.out.println("===Deletar===");
            System.out.println("digite o login do administrador:");
            String loginDeletar = entry.nextLine();
            Admin admindeletar = adminDao.select(loginDeletar);
            System.out.printf("Deseja deletar o admin: %s", admindeletar.getNome());
            System.out.println("1 - sim");
            System.out.println("2 - não");
            int opcaoDelete = entry.nextInt();
            entry.nextLine();
            switch (opcaoDelete) {
                case 1:
                    System.out.println("Digite sua senha: ");
                    String senha = entry.nextLine();
                    if (autenticadorDAO.autenticarAdmin(admin.getLogin(), senha)) {
                        adminDao.delete(loginDeletar);
                        System.out.println("Deseja deletar outro administrador? (S/N)");
                        String resposta = entry.nextLine().toUpperCase();
                        switch (resposta) {
                            case "S":
                                deletarAdmin();
                                break;
                            case "N":
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }
                    }
                case 2:
                    break;
            }
        }
    }

    public void alterarDadosAdmin() {
        Scanner entry = new Scanner(System.in);
        try {
            System.out.println("Digite o nome:\nCaso queira sair digite 0");
            String nomeAlterar = entry.nextLine().toUpperCase();
            if(nomeAlterar.equals("0")){
                return;
            }
            System.out.println("Digite uma senha:");
            System.out.println("deve conter:\n•Letra maúscula e minuscula;\n•Caracter especial;\n•Número;\n•Entre 8 a 12 Caracteres.");
            String senhaAlterar = entry.nextLine();
            Admin admin = new Admin(nomeAlterar, senhaAlterar);
            if (admin.verificaNome() && admin.verificarSenha()) {
                admin.criarLogin();
                int id = this.admin.getId();
                String loginNovo = admin.getLogin();
                adminDao.update(nomeAlterar, senhaAlterar, loginNovo, id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exibirDadosAdmin() {
        System.out.printf("Nome: %s\n", admin.getNome());
        System.out.printf("Login: %s", admin.getLogin());
    }
}
