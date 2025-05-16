package org.example.model;

import com.sun.tools.javac.Main;
import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class MenuUsuario {
    private Aluno aluno;
    private AlunoDAO alunoDAO;

    public MenuUsuario(Aluno aluno) {
        this.aluno = aluno;
        this.alunoDAO = new AlunoDAO();
    }

    public void acessoMenuUsuario() {
        try {
            while (true) {
                AlunoDAO alunoDAO = new AlunoDAO();
                Scanner entry = new Scanner(System.in);

                System.out.println("======MENU======");
                System.out.println("Bem vindo a Uninós.");
                System.out.println("1 - Dados do aluno");
                System.out.println("2 - alterar dados");
                System.out.println("0 - Sair");

                int opcao = entry.nextInt();
                entry.nextLine();
                switch (opcao) {
                    case 1:
                        dadosAluno();
                        break;
                    case 2:
                        alterarDadosAluno();
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
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dadosAluno() {
        CursoDAO cursoDAO = new CursoDAO();

        System.out.println("==DADOS DO ALUNO==");
        System.out.printf("Nome: %s\n", aluno.getNome());
        System.out.printf("Matricula: %d\n", aluno.getMatricula());
        System.out.printf("Telefone: %d\n", aluno.getTelefone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("Data de nascimento: %s\n", aluno.getDataNascimento().toString());
        Curso cursoRecuperar = cursoDAO.getCurso(aluno.getId_curso());
        System.out.printf("Curso: %s\n", cursoRecuperar.getNome());
    }

    public void alterarDadosAluno() {
        Scanner entry = new Scanner(System.in);
        while (true) {
            try {
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

                System.out.println("Digite a nova senha: ");
                String novaSenha = entry.nextLine();

                System.out.println("Escolha o curso:");
                System.out.println("Digite o nome do curso.");
                CursoDAO cursoDAO = new CursoDAO();
                cursoDAO.select();
                String nomeCurso = entry.nextLine();
                Curso curso = cursoDAO.existeCurso(nomeCurso);
                int idNovoCurso = curso.getId();
                long matriculaAntiga = aluno.getMatricula();
                aluno.setNome(nomeAluno);
                aluno.setTelefone(telefone);
                aluno.setDataString(dataString);
                aluno.setCpf(cpf);
                aluno.setId_curso(idNovoCurso);
                aluno.setSenha(novaSenha);
                if (aluno.VerificaDataNascimento() && aluno.verificarSenha() && aluno.verificaNome() && aluno.validarCPF(cpf)) {
                    aluno.DatedataNascimento();
                    alunoDAO.update(aluno, matriculaAntiga);
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
