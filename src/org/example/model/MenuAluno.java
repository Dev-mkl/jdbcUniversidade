package org.example.model;

import com.sun.tools.javac.Main;
import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;

import java.util.Scanner;

public class MenuAluno {
    private Admin admin;
    private AlunoDAO alunoDAO;

    public MenuAluno(Admin admin) {
        this.admin = admin;
        alunoDAO = new AlunoDAO();
    }

    public void acessoMenuAluno() {
        while (true) {
            try {
                Scanner entry = new Scanner(System.in);
                AlunoDAO alunoDAO = new AlunoDAO();

                System.out.println("=====Aluno=====");
                System.out.println("1 - Cadastrar aluno.");
                System.out.println("2 - Excluir aluno.");
                System.out.println("3 - Exibir dados do aluno.");
                System.out.println("4 - Listar alunos.");
                System.out.println("0 - Sair");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarAluno();
                        break;
                    case 2:
                        deletarAluno();
                        break;
                    case 3:
                        exibirDadoAluno();
                        break;
                    case 4:
                        listarAluno();
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

    public void cadastrarAluno() {
        Scanner entry = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("=======Cadastrar aluno======");
                System.out.println("Nome do aluno: ");
                String nomeAluno = entry.nextLine();

                System.out.println("Telefone: ");
                long telefone = entry.nextLong();
                entry.nextLine();

                System.out.println("Data de nascimento:");
                System.out.println("Ex: 12051997 (Dia mês ano)");
                String dataString = entry.nextLine();

                System.out.println("CPF: ");
                System.out.println("Ex: 12345678900");
                String cpf = entry.nextLine();

                System.out.println("Senha: ");
                String senha = entry.nextLine();

                System.out.println("Escolha o curso:");
                System.out.println("Digite o nome do curso.");
                CursoDAO cursoDAO = new CursoDAO();
                cursoDAO.select();
                String nomeCurso = entry.nextLine();
                Curso curso = cursoDAO.existeCurso(nomeCurso);
                Aluno aluno = new Aluno(nomeAluno, telefone, dataString, cpf, curso.getId(), senha);
                if (aluno.VerificaDataNascimento() && aluno.verificaNome() && aluno.verificarSenha() && aluno.validarCPF(cpf)) {
                    aluno.DatedataNascimento();
                    aluno.gerarMatricula();
                    alunoDAO.insert(aluno);
                    break;
                }
                System.out.println("Deseja cadastrar outro aluno? (S/N)");
                String resposta = entry.nextLine();
                switch (resposta) {
                    case "S":
                        cadastrarAluno();
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção invalida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: Aluno não cadastrado." + e.getMessage());
            }
        }
    }

    public void deletarAluno() {
        Scanner entry = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("matricula: ");
                long matriculaDelete = entry.nextInt();
                alunoDAO.delete(matriculaDelete);
                System.out.println("Deseja deletar outro aluno? (S/N)");
                String resposta = entry.nextLine().toUpperCase();
                switch (resposta) {
                    case "S":
                        deletarAluno();
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção invalida!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void exibirDadoAluno() {
        Scanner entry = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Qual aluno que deseja ver os dados:");
                Long matricula = entry.nextLong();
                alunoDAO.select(matricula);
                System.out.println("Deseja ver os dados de outro aluno? (S/N)");
                String resposta = entry.nextLine().toUpperCase();
                switch (resposta) {
                    case "S":
                        exibirDadoAluno();
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Opção invalida!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void listarAluno(){
        try{
            alunoDAO.listarAlunos();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
