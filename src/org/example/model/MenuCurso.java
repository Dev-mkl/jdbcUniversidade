package org.example.model;

import org.example.dao.CursoDAO;
import org.example.model.MenuAdmin;

import java.util.Scanner;

public class MenuCurso {
    private Admin admin;
    private CursoDAO cursoDAO;
    public MenuCurso(Admin admin) {
        this.admin = admin;
        cursoDAO = new CursoDAO();
    }
    public void acessoMenuCurso() {
        while (true) {
            try {
                Scanner entry = new Scanner(System.in);

                System.out.println("=====Curso=====");
                System.out.println("1 - inserir novo curso");
                System.out.println("2 - alterar curso");
                System.out.println("3 - remover curso");
                System.out.println("4 - listar curso");
                System.out.println("0 - Sair");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        inserirCurso();
                        break;
                    case 2:
                        alterarCurso();
                        break;
                    case 3:
                        removerCurso();
                    case 4:
                        listarCurso();
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
                        MenuAdmin menuAdmin = new MenuAdmin(admin);
                        menuAdmin.menuAdministrador();
                    default:
                        System.out.println("Opção invalida.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void alterarCurso() {
        while (true) {
            Scanner entry = new Scanner(System.in);

            System.out.println("Curso a ser alterado:");
            String nomeCurso = entry.nextLine();
            Curso cursoAlterar = cursoDAO.existeCurso(nomeCurso);
            if (cursoAlterar != null) {
                System.out.println("Novo nome:");
                String nomeNovo = entry.next();
                cursoDAO.update(cursoAlterar, nomeNovo);
                System.out.println("Deseja sair? (S/N)");
                String cadastro = entry.nextLine();
                switch (cadastro) {
                    case "S":
                        alterarCurso();
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
    }

    public void inserirCurso() {
        while (true) {
            Scanner entry = new Scanner(System.in);
            System.out.println("Nome do curso: ");
            String nome = entry.nextLine();
            Curso curso = new Curso(nome);
            cursoDAO.insert(curso);
            System.out.println("Deseja sair? (S/N)");
            String cadastro = entry.nextLine();
            switch (cadastro) {
                case "S":
                    inserirCurso();
                    break;
                case "N":
                    break;
            }
        }
    }

    public void removerCurso() {
        while (true) {
            try {
                Scanner entry = new Scanner(System.in);

                System.out.println("Nome do Curso: ");
                String cursoNome = entry.nextLine();

                cursoDAO.delete(cursoNome);
                System.out.println("Deseja sair? (S/N)");
                String cadastro = entry.nextLine();
                switch (cadastro) {
                    case "S":
                        removerCurso();
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Curso não cadastrado.");
            }
        }
    }

    public void listarCurso() {
        try {
            cursoDAO.select();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
