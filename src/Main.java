import org.example.dao.AlunoDAO;
import org.example.dao.AutenticadorDAO;
import org.example.dao.CursoDAO;
import org.example.factory.CreateTable;
import org.example.model.Aluno;
import org.example.model.Curso;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /*CreateTable ct = new CreateTable();
        ct.createTableAluno();
        ct.createTableCurso();
        ct.createTableAdmin();*/

        System.out.println("=== Tela de Login ===");

        System.out.print("Usuário: ");
        String usuario = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        AutenticadorDAO autenticadorDAO = new AutenticadorDAO();
        if(autenticadorDAO.autenticarUsuario(usuario, senha)){
            menuUsuario();
        }
        if(autenticadorDAO.autenticarUsuario(usuario, senha)){
            menuAdmin();
        }

    }

    public static void menuCurso() {
        while (true) {
            CursoDAO cursoDAO = new CursoDAO();
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
                    menuAdmin();
                case 1:
                    System.out.println("Nome do curso: ");
                    String nome = entry.nextLine();
                    Curso curso = new Curso(nome);
                    cursoDAO.insert(curso);
                    break;

                case 2:
                    System.out.println("Curso a ser alterado");
                    String nomeCurso = entry.nextLine();
                    Curso cursoAlterar = cursoDAO.existeCurso(nomeCurso);
                    if (cursoAlterar != null) {
                        System.out.println("Novo nome:");
                        String nomeNovo = entry.next();
                        cursoDAO.update(cursoAlterar, nomeNovo);
                    }
                    break;
                case 3:
                    System.out.println("Nome do Curso: ");
                    String cursoNome = entry.nextLine();
                    try {
                        cursoDAO.delete(cursoNome);
                        break;
                    } catch (Exception e) {
                        System.out.println("Curso não cadastrado.");
                        ;
                    }
                case 4:
                    cursoDAO.select();
                    break;
                default:
                    System.out.println("Opção invalida.");
            }
        }
    }

    public static void menuAluno() {
        while (true) {
            Scanner entry = new Scanner(System.in);
            AlunoDAO alunoDAO = new AlunoDAO();

            System.out.println("=====Aluno=====");
            System.out.println("1 - Cadastrar aluno.");
            System.out.println("3 - Excluir aluno.");
            System.out.println("4 - Exibir dados do aluno.");
            System.out.println("0 - Sair");

            int opcao = entry.nextInt();
            entry.nextLine();
            switch (opcao) {
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
                    menuAdmin();
                case 1:
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

                        System.out.println("Escolha o curso:");
                        System.out.println("Digite o nome do curso.");
                        CursoDAO cursoDAO = new CursoDAO();
                        cursoDAO.select();
                        String nomeCurso = entry.nextLine();
                        Curso curso = cursoDAO.existeCurso(nomeCurso);
                        Aluno aluno = new Aluno(nomeAluno, telefone, dataString, cpf, curso.getId());
                        aluno.VerificaDataNascimento();
                        aluno.DatedataNascimento();
                        aluno.gerarMatricula();
                        alunoDAO.insert(aluno);
                    } catch (Exception e) {
                        System.out.println("Erro: Aluno não cadastrado." + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                    boolean vef = true;
                        System.out.println("==Alterar dados do aluno==");
                        System.out.println("Matrícula do aluno: ");
                        long matriculaAntiga = entry.nextLong();
                        entry.nextLine();

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

                        System.out.println("Escolha o curso:");
                        System.out.println("Digite o nome do curso.");
                        CursoDAO cursoDAO = new CursoDAO();
                        cursoDAO.select();
                        String nomeCurso = entry.nextLine();
                    Curso curso = cursoDAO.existeCurso(nomeCurso);
                    int idNovoCurso = curso.getId();
                    Aluno aluno = alunoDAO.getAluno(matriculaAntiga);
                    if(aluno == null){
                        throw new IllegalArgumentException("Aluno nao encontrado.");
                    }
                    aluno.setNome(nomeAluno);
                    aluno.setTelefone(telefone);
                    aluno.setDataString(dataString);
                    aluno.setCpf(cpf);
                    aluno.setId_curso(idNovoCurso);

                        aluno.VerificaDataNascimento();
                        aluno.DatedataNascimento();
                        aluno.gerarMatricula();
                        alunoDAO.update(aluno, matriculaAntiga);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("matricula do aluno a ser excluido");
                    long matriculaDelete = entry.nextInt();
                    alunoDAO.delete(matriculaDelete);
                    break;
                case 4:
                    System.out.println("Dados");
                    System.out.println("Qual aluno que deseja ver os dados:\n1. esqueci minha matricula");
                    Long matriculaVef = entry.nextLong();
                    if (matriculaVef == 1) {
                        System.out.println("Função ainda não disponivel.");
                        System.out.println("Digite seu cpf: ");
                        entry.nextLine();
                        String cpf = entry.nextLine();
                        alunoDAO.recuperarMatricula(cpf);
                        break;
                    }
                    try {
                        alunoDAO.select(matriculaVef);
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro:" + e.getMessage());
                        break;
                    }
            }


        }
    }
    public static void menuAdmin(){
        while (true) {
            Scanner entry = new Scanner(System.in);

            System.out.println("======MENU======");
            System.out.println("1 - Curso");
            System.out.println("2 - Aluno");
            System.out.println("Escolha uma opção:\n");
            int opcao = entry.nextInt();
            switch (opcao) {
                case 1:
                    menuCurso();
                case 2:
                    menuAluno();
                default:
                    throw new IllegalArgumentException();
            }

        }
    }
}